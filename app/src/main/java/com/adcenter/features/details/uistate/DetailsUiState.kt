package com.adcenter.features.details.uistate

import com.adcenter.features.details.data.DetailsModel

sealed class DetailsUiState {
    object Loading : DetailsUiState()
    data class Success(val result: DetailsModel) : DetailsUiState()
    data class Error(val throwable: Throwable) : DetailsUiState()
}