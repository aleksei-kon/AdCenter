package com.adcenter.features.lastads.models

import com.adcenter.features.lastads.LastAdsConstants.FIRST_PAGE_NUMBER

data class LastAdsRequestParams(
    val pageNumber: Int = FIRST_PAGE_NUMBER,
    val isForceRefresh: Boolean = false
)