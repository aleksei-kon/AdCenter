package com.adcenter.ui.fragments

import com.adcenter.R
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.IPageConfiguration.*

class SettingsFragment : BaseFragment(), IPageConfiguration {

    override fun getToolbarTitle(): String = getString(R.string.settings_title)

    override val layout: Int = R.layout.fragment_settings

    override val toolbarScrollBehaviour: ToolbarScrollBehaviour = ToolbarScrollBehaviour.DISAPPEARS
}