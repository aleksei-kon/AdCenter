package com.adcenter.features.lastads.uistate

import com.adcenter.features.lastads.models.LastAdsModel

sealed class LastAdsUiState {
    object Loading : LastAdsUiState()
    object Pagination : LastAdsUiState()
    class Success(val result: LastAdsModel) : LastAdsUiState()
    class Error(val throwable: Throwable) : LastAdsUiState()
}