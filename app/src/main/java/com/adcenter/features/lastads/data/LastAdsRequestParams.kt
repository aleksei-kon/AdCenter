package com.adcenter.features.lastads.data

import com.adcenter.features.lastads.LastAdsConstants.FIRST_PAGE_NUMBER

data class LastAdsRequestParams(
    val pageNumber: Int = FIRST_PAGE_NUMBER
)