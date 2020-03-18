package com.adcenter.api

import com.adcenter.features.adrequests.data.AdRequestsParams
import com.adcenter.features.bookmarks.data.BookmarksRequestParams
import com.adcenter.features.details.data.DetailsRequestParams
import com.adcenter.features.lastads.data.LastAdsRequestParams
import com.adcenter.features.myads.data.MyAdsRequestParams
import com.adcenter.features.search.data.SearchRequestParams

interface IApi {

    fun getImageDownloadUrl(name: String): String

    fun getImageUploadUrl(): String

    fun getNewDetailsUrl(): String

    fun getDetailsUrl(params: DetailsRequestParams): String

    fun getLastAdsUrl(params: LastAdsRequestParams): String

    fun getMyAdsUrl(params: MyAdsRequestParams): String

    fun getBookmarksUrl(params: BookmarksRequestParams): String

    fun getSearchUrl(params: SearchRequestParams): String

    fun getAdRequestsUrl(params: AdRequestsParams): String

    fun getRegisterUrl(): String

    fun getLoginUrl(): String

    fun getShowHideUrl(id: String): String
}