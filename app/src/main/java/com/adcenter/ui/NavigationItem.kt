package com.adcenter.ui

import com.adcenter.R
import com.adcenter.ui.NavigationItem.NavigationItemId.*
import com.adcenter.utils.resource.IResourceProvider
import org.koin.core.KoinComponent
import org.koin.core.inject

sealed class NavigationItem : KoinComponent {

    protected val resourceDependency: IResourceProvider by inject()

    abstract val id: Int

    abstract val title: String

    abstract val iconRes: Int

    class LastAdsItem : NavigationItem() {

        override val id: Int = LAST_ADS.ordinal

        override val title: String = resourceDependency.lastAdsTitle

        override val iconRes: Int = R.drawable.ic_last_ads
    }

    class SearchItem : NavigationItem() {

        override val id: Int = SEARCH.ordinal

        override val title: String = resourceDependency.searchTitle

        override val iconRes: Int = R.drawable.ic_search
    }

    class MyAdsItem : NavigationItem() {

        override val id: Int = MY_ADS.ordinal

        override val title: String = resourceDependency.myAdsTitle

        override val iconRes: Int = R.drawable.ic_my_ads
    }

    class BookmarksItem : NavigationItem() {

        override val id: Int = BOOKMARKS.ordinal

        override val title: String = resourceDependency.bookmarksTitle

        override val iconRes: Int = R.drawable.ic_bookmark
    }


    class AdRequestsItem : NavigationItem() {

        override val id: Int = AD_REQUESTS.ordinal

        override val title: String = resourceDependency.adRequestsTitle

        override val iconRes: Int = R.drawable.ic_ad_requests
    }

    class SettingsItem : NavigationItem() {

        override val id: Int = SETTINGS.ordinal

        override val title: String = resourceDependency.settingsTitle

        override val iconRes: Int = R.drawable.ic_settings
    }

    enum class NavigationItemId {
        LAST_ADS,
        SEARCH,
        BOOKMARKS,
        MY_ADS,
        AD_REQUESTS,
        SETTINGS
    }
}
