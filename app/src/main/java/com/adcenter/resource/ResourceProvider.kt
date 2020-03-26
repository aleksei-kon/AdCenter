package com.adcenter.resource

import android.content.Context
import com.adcenter.R

class ResourceProvider(private val context: Context) :
    IResourceProvider {

    private fun getString(resId: Int) = context.getString(resId)

    override val adRequestsTitle: String = getString(R.string.AD_REQUESTS_TITLE)

    override val bookmarksTitle: String = getString(R.string.BOOKMARKS_TITLE)

    override val lastAdsTitle: String = getString(R.string.LAST_ADS_TITLE)

    override val myAdsTitle: String = getString(R.string.MY_ADS_TITLE)

    override val searchTitle: String = getString(R.string.SEARCH_TITLE)

    override val settingsTitle: String = getString(R.string.SETTINGS_TITLE)

    override val datePrefix: String = getString(R.string.DATEPREFIX)

    override val viewsPrefix: String = getString(R.string.VIEWSPREFIX)
}