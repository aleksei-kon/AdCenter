package com.adcenter.features.search.data

import com.adcenter.features.bookmarks.BookmarksConstants
import com.adcenter.utils.Constants.EMPTY

data class SearchRequestParams(
    val searchText: String = EMPTY,
    val pageNumber: Int = BookmarksConstants.FIRST_PAGE_NUMBER,
    val sortType: Int = 0
)