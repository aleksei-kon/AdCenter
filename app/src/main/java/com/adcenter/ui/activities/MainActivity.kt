package com.adcenter.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.adcenter.R
import com.adcenter.config.IAppConfig
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.extensions.gone
import com.adcenter.extensions.isConnectedToNetwork
import com.adcenter.extensions.visible
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : OfflineActivity() {

    @Inject
    lateinit var appConfig: IAppConfig

    init {
        Injector.appComponent.inject(this)
    }

    private val defaultItemId: Int = R.id.lastAdsItem

    override val layout: Int = R.layout.activity_main

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_app_bar_manu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        selectItem(item.itemId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(bottomAppBar)
        initFragmentManager()
        updateContentVisibility()

        if (savedInstanceState == null) {
            selectItem(defaultItemId)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        selectItem(defaultItemId)
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

    private fun initFragmentManager() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(
            fragmentLifecycleCallback,
            false
        )
    }

    private fun selectItem(itemId: Int): Boolean {
        val fragment: BaseFragment = when (itemId) {
            R.id.lastAdsItem -> LastAdsFragment()
            R.id.myAdsItem -> MyAdsFragment()
            R.id.bookmarksItem -> BookmarksFragment()
            R.id.settingsItem -> SettingsFragment()
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

    private val fragmentLifecycleCallback = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fragmentManager: FragmentManager,
            fragment: Fragment,
            view: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fragmentManager, fragment, view, savedInstanceState)

            if (fragment is IPageConfiguration) {
                appBarTitle.text = fragment.toolbarTitle
            } else {
                appBarTitle.text = getString(R.string.app_name)
            }
        }
    }
}
