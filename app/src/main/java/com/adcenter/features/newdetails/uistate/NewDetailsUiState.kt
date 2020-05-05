package com.adcenter.features.newdetails.uistate

import android.net.Uri

sealed class NewDetailsUiState
object WaitLoading : NewDetailsUiState()
object Success : NewDetailsUiState()
data class UpdatePhotos(val list: List<Uri>) : NewDetailsUiState()
data class Error(val throwable: Throwable) : NewDetailsUiState()