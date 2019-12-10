package com.adcenter.myads.data

import com.adcenter.bookmarks.BookmarksConstants.FIRST_PAGE_NUMBER

data class MyAdsRequestParams(
    val pageNumber: Int = FIRST_PAGE_NUMBER
)