package com.adcenter.features.newdetails.uistate

import com.adcenter.features.newdetails.models.NewDetailsModel

sealed class NewDetailsUiState {
    object WaitLoading : NewDetailsUiState()
    data class Success(val result: NewDetailsModel) : NewDetailsUiState()
    data class Error(val throwable: Throwable) : NewDetailsUiState()
}