package com.adcenter.features.adrequests.uistate

import com.adcenter.features.adrequests.models.AdRequestsModel

sealed class AdRequestsUiState {
    object Loading : AdRequestsUiState()
    object Pagination : AdRequestsUiState()
    data class Success(val result: AdRequestsModel) : AdRequestsUiState()
    data class Error(val throwable: Throwable) : AdRequestsUiState()
}