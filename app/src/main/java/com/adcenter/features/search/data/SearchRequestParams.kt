package com.adcenter.features.search.data

import com.adcenter.features.search.SearchConstants.FIRST_PAGE_NUMBER
import com.adcenter.utils.Constants.EMPTY

data class SearchRequestParams(
    val searchText: String = EMPTY,
    val pageNumber: Int = FIRST_PAGE_NUMBER,
    val sortType: Int = 0
)