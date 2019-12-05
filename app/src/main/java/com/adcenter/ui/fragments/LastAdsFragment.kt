package com.adcenter.ui.fragments

import com.adcenter.R
import com.adcenter.ui.IPageConfiguration

class LastAdsFragment : BaseFragment(), IPageConfiguration {

    override fun getToolbarTitle(): String = getString(R.string.last_ads_title)

    override val layout: Int = R.layout.fragment_last_ads
}