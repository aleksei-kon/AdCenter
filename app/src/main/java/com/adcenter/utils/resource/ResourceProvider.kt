package com.adcenter.utils.resource

import android.content.Context
import com.adcenter.R

class ResourceProvider(private val context: Context) :
    IResourceProvider {

    private fun getString(resId: Int) = context.getString(resId)

    override val bookmarksTitle: String = getString(R.string.bookmarks_title)

    override val lastAdsTitle: String = getString(R.string.last_ads_title)

    override val myAdsTitle: String = getString(R.string.my_ads_title)

    override val searchTitle: String = getString(R.string.search_title)

    override val settingsTitle: String = getString(R.string.settings_title)

    override val datePrefix: String = getString(R.string.datePrefix)

    override val viewsPrefix: String = getString(R.string.viewsPrefix)
}