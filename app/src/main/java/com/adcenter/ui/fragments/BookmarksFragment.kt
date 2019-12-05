package com.adcenter.ui.fragments

import com.adcenter.R
import com.adcenter.ui.IPageConfiguration

class BookmarksFragment : BaseFragment(), IPageConfiguration {

    override fun getToolbarTitle(): String = getString(R.string.bookmarks_title)

    override val layout: Int = R.layout.fragment_bookmarks
}