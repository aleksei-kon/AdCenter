package com.adcenter.features.myads.models

import com.adcenter.features.bookmarks.BookmarksConstants.FIRST_PAGE_NUMBER

data class MyAdsRequestParams(
    val pageNumber: Int = FIRST_PAGE_NUMBER
)