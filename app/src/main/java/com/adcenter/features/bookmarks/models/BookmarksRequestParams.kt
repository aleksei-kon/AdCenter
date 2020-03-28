package com.adcenter.features.bookmarks.models

import com.adcenter.features.bookmarks.BookmarksConstants.FIRST_PAGE_NUMBER

data class BookmarksRequestParams(
    val pageNumber: Int = FIRST_PAGE_NUMBER
)