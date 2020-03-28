package com.adcenter.features.myads.uistate

import com.adcenter.features.myads.models.MyAdsModel

sealed class MyAdsUiState {
    object Loading : MyAdsUiState()
    object Pagination : MyAdsUiState()
    data class Success(val result: MyAdsModel) : MyAdsUiState()
    data class Error(val throwable: Throwable) : MyAdsUiState()
}