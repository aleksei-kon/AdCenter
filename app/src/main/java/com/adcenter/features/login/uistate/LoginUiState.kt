package com.adcenter.features.login.uistate

import com.adcenter.entities.view.AppConfigInfo

sealed class LoginUiState
object WaitLogin : LoginUiState()
data class Success(val result: AppConfigInfo) : LoginUiState()
data class Error(val throwable: Throwable) : LoginUiState()