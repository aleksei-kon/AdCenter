package com.adcenter.features.lastads.uistate

import com.adcenter.features.lastads.data.LastAdsModel

sealed class LastAdsUiState {
    object Loading : LastAdsUiState()
    data class Success(val result: LastAdsModel) : LastAdsUiState()
    data class Error(val throwable: Throwable) : LastAdsUiState()
}