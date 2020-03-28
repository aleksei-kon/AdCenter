package com.adcenter.features.search.models

import com.adcenter.features.search.SearchConstants.FIRST_PAGE_NUMBER
import com.adcenter.extensions.Constants.EMPTY

data class SearchRequestParams(
    val searchText: String = EMPTY,
    val pageNumber: Int = FIRST_PAGE_NUMBER,
    val sortType: Int = 0
)