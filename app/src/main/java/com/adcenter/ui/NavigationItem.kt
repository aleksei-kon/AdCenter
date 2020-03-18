package com.adcenter.ui

import com.adcenter.R
import com.adcenter.app.App
import com.adcenter.ui.NavigationItem.NavigationItemId.*
import com.adcenter.resource.IResourceProvider
import org.koin.core.KoinComponent
import org.koin.core.inject
import javax.inject.Inject

sealed class NavigationItem : KoinComponent {

    @Inject
    lateinit var resourceDependency: IResourceProvider

    init {
        App.appComponent.inject(this)
    }

    abstract val id: Int

    abstract val title: String

    abstract val iconRes: Int

    class LastAdsItem : NavigationItem() {

        override val id: Int = LAST_ADS.ordinal

        override val title: String = resourceDependency.lastAdsTitle

        override val iconRes: Int = R.drawable.ic_menu_last_ads
    }

    class SearchItem : NavigationItem() {

        override val id: Int = SEARCH.ordinal

        override val title: String = resourceDependency.searchTitle

        override val iconRes: Int = R.drawable.ic_menu_search
    }

    class MyAdsItem : NavigationItem() {

        override val id: Int = MY_ADS.ordinal

        override val title: String = resourceDependency.myAdsTitle

        override val iconRes: Int = R.drawable.ic_menu_my_ads
    }

    class BookmarksItem : NavigationItem() {

        override val id: Int = BOOKMARKS.ordinal

        override val title: String = resourceDependency.bookmarksTitle

        override val iconRes: Int = R.drawable.ic_menu_bookmarks
    }


    class AdRequestsItem : NavigationItem() {

        override val id: Int = AD_REQUESTS.ordinal

        override val title: String = resourceDependency.adRequestsTitle

        override val iconRes: Int = R.drawable.ic_menu_ad_requests
    }

    class SettingsItem : NavigationItem() {

        override val id: Int = SETTINGS.ordinal

        override val title: String = resourceDependency.settingsTitle

        override val iconRes: Int = R.drawable.ic_menu_settings
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
