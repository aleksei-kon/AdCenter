package com.adcenter.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.adcenter.R
import com.adcenter.appconfig.IAppConfig
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.extensions.gone
import com.adcenter.extensions.isConnectedToNetwork
import com.adcenter.extensions.visible
import com.adcenter.ui.bottomsheet.NavigationBottomSheetDialogFragment
import com.adcenter.ui.common.IPageConfiguration
import com.adcenter.ui.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.content
import kotlinx.android.synthetic.main.activity_new_ad.*
import javax.inject.Inject

private const val CURRENT_MENU_ITEM_ID_KEY = "currentMenuItemId"
private const val SEARCH_VISIBILITY = "searchVisibility"
private const val FAB_VISIBILITY = "fabVisibility"

class MainActivity : OfflineActivity() {

    @Inject
    lateinit var appConfig: IAppConfig

    init {
        Injector.appComponent.inject(this)
    }

    private val defaultItemId: Int = R.id.lastAdsItem

    private var currentMenuItemId: Int = -1

    private val navigationBottomSheet = NavigationBottomSheetDialogFragment()
        .apply {
            onItemSelectedListener = ::selectItem
        }

    override val layout: Int = R.layout.activity_main

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_MENU_ITEM_ID_KEY, currentMenuItemId)
        outState.putInt(SEARCH_VISIBILITY, searchButton.visibility)
        outState.putBoolean(FAB_VISIBILITY, addFab.isOrWillBeShown)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentMenuItemId = savedInstanceState.getInt(CURRENT_MENU_ITEM_ID_KEY)
        searchButton.visibility = savedInstanceState.getInt(SEARCH_VISIBILITY)

        if (savedInstanceState.getBoolean(FAB_VISIBILITY)) {
            addFab.show()
        } else {
            addFab.hide()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBottomAppBar()
        initFragmentManager()
        updateContentVisibility()

        searchButton.setOnClickListener {
            startActivityForResult(Intent(this, SearchActivity::class.java), 123)
        }

        addFab.setOnClickListener {
            val activityClass = if (appConfig.isLoggedIn || appConfig.isAdmin) {
                NewAdActivity::class.java
            } else {
                LoginActivity::class.java
            }

            startActivityForResult(Intent(this, activityClass), 123)
        }

        if (savedInstanceState == null) {
            selectItem(defaultItemId)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        updateCurrentFragment()
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

    private fun showSearch() {
        searchButton.visible()
    }

    private fun hideSearch() {
        searchButton.gone()
    }

    private fun hideAdd() {
        addFab.hide()
    }

    private fun showAdd() {
        addFab.show()
    }

    private fun initBottomAppBar() {
        setSupportActionBar(bottomAppBar)
        bottomAppBar.setNavigationOnClickListener {
            navigationBottomSheet.show(supportFragmentManager, null)
        }
    }

    private fun initFragmentManager() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(
            fragmentLifecycleCallback,
            false
        )
    }

    private fun updateCurrentFragment() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.content)

        if (currentFragment is IUpdatableFragment) {
            currentFragment.updateData()
        }
    }

    private fun selectItem(itemId: Int): Boolean {
        if (itemId == currentMenuItemId) {
            return true
        }

        val fragment: BaseFragment = when (itemId) {
            R.id.lastAdsItem -> LastAdsFragment()
            R.id.myAdsItem -> MyAdsFragment()
            R.id.bookmarksItem -> BookmarksFragment()
            R.id.adRequestsItem -> AdRequestsFragment()
            R.id.settingsItem -> SettingsFragment()
            else -> return false
        }

        setFragment(fragment)
        currentMenuItemId = itemId

        return true
    }

    private fun setFragment(fragment: BaseFragment) {
        if (fragment is SettingsFragment) {
            hideAdd()
            hideSearch()
        } else {
            showAdd()
            showSearch()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, fragment)
            .commit()
    }

    private val fragmentLifecycleCallback = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fragmentManager: FragmentManager,
            fragment: Fragment,
            view: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fragmentManager, fragment, view, savedInstanceState)

            when (fragment) {
                is NavigationBottomSheetDialogFragment -> return
                is IPageConfiguration -> appBarTitle.text = fragment.toolbarTitle
                else -> appBarTitle.text = getString(R.string.app_name)
            }
        }
    }
}
