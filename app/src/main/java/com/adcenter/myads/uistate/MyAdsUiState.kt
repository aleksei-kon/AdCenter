package com.adcenter.myads.uistate

import com.adcenter.myads.data.MyAdsModel

sealed class MyAdsUiState {
    object Loading : MyAdsUiState()
    data class Success(val result: MyAdsModel) : MyAdsUiState()
    data class Error(val throwable: Throwable) : MyAdsUiState()
}