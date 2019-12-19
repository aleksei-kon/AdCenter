package com.adcenter.data

import com.adcenter.app.config.AppConfig
import com.adcenter.features.adrequests.data.AdRequestsParams
import com.adcenter.features.bookmarks.data.BookmarksRequestParams
import com.adcenter.features.details.data.DetailsRequestParams
import com.adcenter.features.lastads.data.LastAdsRequestParams
import com.adcenter.features.myads.data.MyAdsRequestParams
import com.adcenter.features.search.data.SearchRequestParams

fun getImageDownloadUrl(name: String) =
    "$HTTP${AppConfig.backendUrl}/$IMAGE/$DOWNLOAD/$name"

fun getImageUploadUrl() =
    "$HTTP${AppConfig.backendUrl}/$IMAGE/$UPLOAD"

fun getNewDetailsUrl() =
    "$HTTP${AppConfig.backendUrl}/$ADVERT/$NEW_DETAILS"

fun getDetailsUrl(params: DetailsRequestParams) =
    "$HTTP${AppConfig.backendUrl}/$ADVERT/$DETAILS?$DETAILS_ID=${params.detailsId}"

fun getLastAdsUrl(params: LastAdsRequestParams) =
    "$HTTP${AppConfig.backendUrl}/$ADVERT/$LAST_ADS?$PAGE_NUMBER=${params.pageNumber}"

fun getMyAdsUrl(params: MyAdsRequestParams) =
    "$HTTP${AppConfig.backendUrl}/$ADVERT/$MY_ADS?$PAGE_NUMBER=${params.pageNumber}"

fun getBookmarksUrl(params: BookmarksRequestParams) =
    "$HTTP${AppConfig.backendUrl}/$ADVERT/$BOOKMARKS?$PAGE_NUMBER=${params.pageNumber}"

fun getSearchUrl(params: SearchRequestParams) =
    "$HTTP${AppConfig.backendUrl}/$ADVERT/$SEARCH?" +
            "$SEARCH_TEXT=${params.searchText}&" +
            "$PAGE_NUMBER=${params.pageNumber}&" +
            "$SORT_TYPE=${params.sortType}"

fun getAdRequestsUrl(params: AdRequestsParams) =
    "$HTTP${AppConfig.backendUrl}/$ADVERT/$AD_REQUESTS?$PAGE_NUMBER=${params.pageNumber}"

fun getRegisterUrl() =
    "$HTTP${AppConfig.backendUrl}/$ACCOUNT/$REGISTER"

fun getLoginUrl() =
    "$HTTP${AppConfig.backendUrl}/$ACCOUNT/$LOGIN"

fun getShowHideUrl(id: String) =
    "$HTTP${AppConfig.backendUrl}/$ADVERT/$SHOW_HIDE_AD?$ID=$id"

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
