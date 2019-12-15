package com.adcenter.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.adcenter.R
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

class MainActivity : OfflineActivity() {

    override val layout: Int = R.layout.activity_main

    private val defaultNavigationItem: NavigationItem = LastAdsItem()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initNavigation()
        initFragmentManager()

        updateContentVisibility()

        if (savedInstanceState == null) {
            selectItem(defaultNavigationItem.id)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        initNavigation()
        selectItem(defaultNavigationItem.id)
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
        initMenuItem(MyAdsItem())
        initMenuItem(BookmarksItem())
        initMenuItem(SettingsItem())

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            selectItem(menuItem.itemId)
        }
    }

    private fun initMenuItem(item: NavigationItem) {
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
