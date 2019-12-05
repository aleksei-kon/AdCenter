package com.adcenter.ui.fragments

import com.adcenter.R
import com.adcenter.ui.IPageConfiguration

class SearchFragment : BaseFragment(), IPageConfiguration {

    override fun getToolbarTitle(): String = getString(R.string.search_title)

    override val layout: Int = R.layout.fragment_search
}