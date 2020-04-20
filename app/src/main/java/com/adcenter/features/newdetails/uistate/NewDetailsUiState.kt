package com.adcenter.features.newdetails.uistate

sealed class NewDetailsUiState
object WaitLoading : NewDetailsUiState()
object Success : NewDetailsUiState()
data class Error(val throwable: Throwable) : NewDetailsUiState()