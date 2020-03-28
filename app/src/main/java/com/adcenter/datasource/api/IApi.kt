package com.adcenter.datasource.api

import com.adcenter.features.adrequests.models.AdRequestsParams
import com.adcenter.features.bookmarks.models.BookmarksRequestParams
import com.adcenter.features.details.models.DetailsRequestParams
import com.adcenter.features.lastads.models.LastAdsRequestParams
import com.adcenter.features.myads.models.MyAdsRequestParams
import com.adcenter.features.search.models.SearchRequestParams

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