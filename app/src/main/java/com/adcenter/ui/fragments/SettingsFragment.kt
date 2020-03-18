package com.adcenter.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.adcenter.R
import com.adcenter.app.App
import com.adcenter.config.AppConfig
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.extensions.gone
import com.adcenter.extensions.visible
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.IPageConfiguration.ToolbarScrollBehaviour
import com.adcenter.ui.activities.DevSettingsActivity
import com.adcenter.ui.activities.LoginActivity
import com.adcenter.theme.IThemeManager
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseFragment(), IPageConfiguration {

    override val toolbarTitle: String by lazy { getString(R.string.settings_title) }

    override val layout: Int = R.layout.fragment_settings

    override val toolbarScrollBehaviour: ToolbarScrollBehaviour = ToolbarScrollBehaviour.POSITIONED

    @Inject
    lateinit var themeManager: IThemeManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        App.appComponent.inject(this)

        if (AppConfig.isLoggedIn) {
            loginButton.gone()
            logoutButton.visible()
        } else {
            loginButton.visible()
            logoutButton.gone()
        }

        loginButton.setOnClickListener {
            requireContext().startActivity(
                Intent(
                    requireContext(),
                    LoginActivity::class.java
                )
            )
        }

        logoutButton.setOnClickListener {
            AppConfig.updateConfig(AppConfigInfo())
            requireActivity().recreate()
        }

        switchThemeButton.setOnClickListener {
            themeManager.switchTheme()
            requireActivity().recreate()
        }

        devSettingsButton.setOnClickListener {
            requireContext().startActivity(
                Intent(
                    requireContext(),
                    DevSettingsActivity::class.java
                )
            )
        }
    }
}