package com.adcenter.bookmarks.data

import com.adcenter.bookmarks.BookmarksConstants.FIRST_PAGE_NUMBER

data class BookmarksRequestParams(
    val pageNumber: Int = FIRST_PAGE_NUMBER
)