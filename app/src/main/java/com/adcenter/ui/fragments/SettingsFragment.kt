package com.adcenter.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.adcenter.R
import com.adcenter.config.IAppConfig
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.extensions.gone
import com.adcenter.extensions.visible
import com.adcenter.resource.IResourceProvider
import com.adcenter.theme.IThemeManager
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.activities.DevSettingsActivity
import com.adcenter.ui.activities.LoginActivity
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseFragment(), IPageConfiguration {

    @Inject
    lateinit var themeManager: IThemeManager

    @Inject
    lateinit var resourceProvider: IResourceProvider

    @Inject
    lateinit var appConfig: IAppConfig

    init {
        Injector.appComponent.inject(this)
    }

    override val layout: Int = R.layout.fragment_settings

    override val toolbarTitle: String
        get() = resourceProvider.settingsTitle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (appConfig.isLoggedIn) {
            loginButton.gone()
            logoutButton.visible()
        } else {
            loginButton.visible()
            logoutButton.gone()
        }

        loginButton.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), LoginActivity::class.java)
            )
        }

        logoutButton.setOnClickListener {
            appConfig.updateConfig(AppConfigInfo())
            requireActivity().recreate()
        }

        switchThemeButton.setOnClickListener {
            themeManager.switchTheme()
            requireActivity().recreate()
        }

        devSettingsButton.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), DevSettingsActivity::class.java)
            )
        }
    }
}