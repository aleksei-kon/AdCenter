package com.adcenter.ui.fragments

import com.adcenter.R
import com.adcenter.ui.IPageConfiguration

class MyAdsFragment : BaseFragment(), IPageConfiguration {

    override fun getToolbarTitle(): String = getString(R.string.my_ads_title)

    override val layout: Int = R.layout.fragment_my_ads
}