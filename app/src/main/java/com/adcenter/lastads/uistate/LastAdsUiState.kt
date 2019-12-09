package com.adcenter.lastads.uistate

import com.adcenter.lastads.data.LastAdsModel

sealed class LastAdsUiState {
    object Loading : LastAdsUiState()
    data class Success(val result: LastAdsModel) : LastAdsUiState()
    data class Error(val throwable: Throwable) : LastAdsUiState()
}