package com.adcenter.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.adcenter.R
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.NavigationItem
import com.adcenter.ui.NavigationItem.*
import com.adcenter.ui.NavigationItem.NavigationItemId.*
import com.adcenter.ui.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_main

    private val defaultNavigationItem: NavigationItem = LastAdsItem()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initNavigation()

        supportFragmentManager.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentViewCreated(
                    fragmentManager: FragmentManager,
                    fragment: Fragment,
                    view: View,
                    savedInstanceState: Bundle?
                ) {
                    super.onFragmentViewCreated(fragmentManager, fragment, view, savedInstanceState)

                    if (fragment is IPageConfiguration) {
                        title = fragment.getToolbarTitle()
                    }
                }
            }, false
        )

        if (savedInstanceState == null) {
            selectItem(defaultNavigationItem.id)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initNavigation() {
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
}
