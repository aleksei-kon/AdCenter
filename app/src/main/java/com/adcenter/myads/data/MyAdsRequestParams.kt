package com.adcenter.myads.data

import com.adcenter.lastads.LastAdsConstants

data class MyAdsRequestParams(
    val pageNumber: Int = LastAdsConstants.FIRST_PAGE_NUMBER
)