package com.adcenter.ui.fragments

import android.os.Bundle
import android.view.View
import com.adcenter.R
import com.adcenter.app.theme.IThemeManager
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.IPageConfiguration.ToolbarScrollBehaviour
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.ext.android.inject

class SettingsFragment : BaseFragment(), IPageConfiguration {

    override val toolbarTitle: String by lazy { getString(R.string.settings_title) }

    override val layout: Int = R.layout.fragment_settings

    override val toolbarScrollBehaviour: ToolbarScrollBehaviour = ToolbarScrollBehaviour.DISAPPEARS

    private val themeManager: IThemeManager by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        themeSwitch.setOnClickListener {
            themeManager.switchTheme()
            activity?.recreate()
        }
    }
}