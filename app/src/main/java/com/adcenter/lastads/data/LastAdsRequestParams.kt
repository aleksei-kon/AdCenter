package com.adcenter.lastads.data

import com.adcenter.lastads.LastAdsConstants.FIRST_PAGE_NUMBER

data class LastAdsRequestParams(
    val pageNumber: Int = FIRST_PAGE_NUMBER
)