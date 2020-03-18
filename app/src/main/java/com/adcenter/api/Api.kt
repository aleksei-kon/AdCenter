package com.adcenter.api

import com.adcenter.config.IAppConfig
import com.adcenter.features.adrequests.data.AdRequestsParams
import com.adcenter.features.bookmarks.data.BookmarksRequestParams
import com.adcenter.features.details.data.DetailsRequestParams
import com.adcenter.features.lastads.data.LastAdsRequestParams
import com.adcenter.features.myads.data.MyAdsRequestParams
import com.adcenter.features.search.data.SearchRequestParams

private const val HTTP = "http://"
private const val HTTPS = "https://"

private const val ADVERT = "advert"
private const val IMAGE = "image"
private const val DOWNLOAD = "download"
private const val UPLOAD = "upload"
private const val BOOKMARKS = "bookmarks"
private const val LAST_ADS = "lastAds"
private const val MY_ADS = "myAds"
private const val SEARCH = "search"
private const val DETAILS = "details"
private const val AD_REQUESTS = "adRequests"
private const val NEW_DETAILS = "newDetails"
private const val ACCOUNT = "account"
private const val LOGIN = "login"
private const val REGISTER = "register"
private const val SHOW_HIDE_AD = "showHideAd"

private const val PAGE_NUMBER = "pageNumber"
private const val SEARCH_TEXT = "searchText"
private const val SORT_TYPE = "sortType"
private const val DETAILS_ID = "detailsId"
private const val ID = "id"

class Api(private val appConfig: IAppConfig) : IApi {

    override fun getImageDownloadUrl(name: String) =
        "$HTTP${appConfig.backendUrl}/$IMAGE/$DOWNLOAD/$name"

    override fun getImageUploadUrl() =
        "$HTTP${appConfig.backendUrl}/$IMAGE/$UPLOAD"

    override fun getNewDetailsUrl() =
        "$HTTP${appConfig.backendUrl}/$ADVERT/$NEW_DETAILS"

    override fun getDetailsUrl(params: DetailsRequestParams) =
        "$HTTP${appConfig.backendUrl}/$ADVERT/$DETAILS?$DETAILS_ID=${params.detailsId}"

    override fun getLastAdsUrl(params: LastAdsRequestParams) =
        "$HTTP${appConfig.backendUrl}/$ADVERT/$LAST_ADS?$PAGE_NUMBER=${params.pageNumber}"

    override fun getMyAdsUrl(params: MyAdsRequestParams) =
        "$HTTP${appConfig.backendUrl}/$ADVERT/$MY_ADS?$PAGE_NUMBER=${params.pageNumber}"

    override fun getBookmarksUrl(params: BookmarksRequestParams) =
        "$HTTP${appConfig.backendUrl}/$ADVERT/$BOOKMARKS?$PAGE_NUMBER=${params.pageNumber}"

    override fun getSearchUrl(params: SearchRequestParams) =
        "$HTTP${appConfig.backendUrl}/$ADVERT/$SEARCH?" +
                "$SEARCH_TEXT=${params.searchText}&" +
                "$PAGE_NUMBER=${params.pageNumber}&" +
                "$SORT_TYPE=${params.sortType}"

    override fun getAdRequestsUrl(params: AdRequestsParams) =
        "$HTTP${appConfig.backendUrl}/$ADVERT/$AD_REQUESTS?$PAGE_NUMBER=${params.pageNumber}"

    override fun getRegisterUrl() =
        "$HTTP${appConfig.backendUrl}/$ACCOUNT/$REGISTER"

    override fun getLoginUrl() =
        "$HTTP${appConfig.backendUrl}/$ACCOUNT/$LOGIN"

    override fun getShowHideUrl(id: String) =
        "$HTTP${appConfig.backendUrl}/$ADVERT/$SHOW_HIDE_AD?$ID=$id"
}
