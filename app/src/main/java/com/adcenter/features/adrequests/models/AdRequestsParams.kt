package com.adcenter.features.adrequests.models

import com.adcenter.features.adrequests.AdRequestsConstants.FIRST_PAGE_NUMBER

data class AdRequestsParams(
    val pageNumber: Int = FIRST_PAGE_NUMBER,
    val isForceRefresh: Boolean = false
)