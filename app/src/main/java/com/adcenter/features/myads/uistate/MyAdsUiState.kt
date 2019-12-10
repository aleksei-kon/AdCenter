package com.adcenter.features.myads.uistate

import com.adcenter.features.myads.data.MyAdsModel

sealed class MyAdsUiState {
    object Loading : MyAdsUiState()
    data class Success(val result: MyAdsModel) : MyAdsUiState()
    data class Error(val throwable: Throwable) : MyAdsUiState()
}