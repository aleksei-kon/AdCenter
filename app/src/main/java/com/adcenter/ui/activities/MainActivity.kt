package com.adcenter.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.adcenter.R
import com.adcenter.config.IAppConfig
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.extensions.gone
import com.adcenter.extensions.isConnectedToNetwork
import com.adcenter.extensions.visible
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.IPageConfiguration.ToolbarScrollBehaviour
import com.adcenter.ui.NavigationItem
import com.adcenter.ui.NavigationItem.*
import com.adcenter.ui.NavigationItem.NavigationItemId.*
import com.adcenter.ui.fragments.*
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : OfflineActivity() {

    @Inject
    lateinit var appConfig: IAppConfig

    init {
        Injector.appComponent.inject(this)
    }

    override val layout: Int = R.layout.activity_main

    private val defaultNavigationItem: NavigationItem = LastAdsItem()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initNavigation()
        initFragmentManager()
        initNewAdButton()

        if (appConfig.isLoggedIn) {
            newAdButton.visible()
        } else {
            newAdButton.gone()
        }

        newAdButton.setOnClickListener {
            startActivity(Intent(this, NewAdActivity::class.java))
        }

        updateContentVisibility()

        if (savedInstanceState == null) {
            selectItem(defaultNavigationItem.id)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        initNavigation()
        selectItem(defaultNavigationItem.id)
        initNewAdButton()
    }

    private fun initNewAdButton() {
        if (appConfig.isAdmin || !appConfig.isLoggedIn) {
            newAdButton.visible()
        } else {
            newAdButton.visible()
        }
    }

    private fun initFragmentManager() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(
            fragmentLifecycleCallback,
            false
        )
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        setupLayoutConfiguration(ToolbarScrollBehaviour.POSITIONED)
    }

    private fun initNavigation() {
        bottomNavigationView.menu.clear()

        initMenuItem(LastAdsItem())
        initMenuItem(SearchItem())

        if (appConfig.isLoggedIn && !appConfig.isAdmin) {
            initMenuItem(BookmarksItem())
            initMenuItem(MyAdsItem())
        }

        if (appConfig.isAdmin) {
            initMenuItem(AdRequestsItem())
        }

        initMenuItem(SettingsItem())

        bottomNavigationView.setOnNavigationItemReselectedListener { }
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            selectItem(menuItem.itemId)
        }
    }

    private fun initMenuItem(item: NavigationItem) {
        if (bottomNavigationView.menu.size == 5) {
            return
        }

        bottomNavigationView.menu.add(
            Menu.NONE,
            item.id,
            Menu.NONE,
            item.title
        ).setIcon(item.iconRes)
    }

    private fun selectItem(itemId: Int): Boolean {
        val fragment: BaseFragment = when (itemId) {
            LAST_ADS.ordinal -> LastAdsFragment()
            SEARCH.ordinal -> SearchFragment()
            MY_ADS.ordinal -> MyAdsFragment()
            BOOKMARKS.ordinal -> BookmarksFragment()
            AD_REQUESTS.ordinal -> AdRequestsFragment()
            SETTINGS.ordinal -> SettingsFragment()
            else -> return false
        }

        setFragment(fragment)

        return true
    }

    private fun setFragment(fragment: BaseFragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, fragment)
            .commit()
    }

    private fun setupLayoutConfiguration(toolbarScrollBehaviour: ToolbarScrollBehaviour) {
        val params = toolbarContainer.layoutParams

        if (params is AppBarLayout.LayoutParams) {
            when (toolbarScrollBehaviour) {
                ToolbarScrollBehaviour.POSITIONED -> {
                    params.scrollFlags = 0
                }
                ToolbarScrollBehaviour.DISAPPEARS -> {
                    params.scrollFlags =
                        AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                }
            }
        }

        toolbarContainer.layoutParams = params
        appBar.requestLayout()
    }

    override fun updateContentVisibility() {
        if (isConnectedToNetwork()) {
            offlineMessage.gone()
            content.visible()
        } else {
            content.gone()
            offlineMessage.visible()
        }
    }

    private val fragmentLifecycleCallback = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fragmentManager: FragmentManager,
            fragment: Fragment,
            view: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fragmentManager, fragment, view, savedInstanceState)

            if (fragment is IPageConfiguration) {
                title = fragment.toolbarTitle
                setupLayoutConfiguration(fragment.toolbarScrollBehaviour)
            }
        }
    }
}
