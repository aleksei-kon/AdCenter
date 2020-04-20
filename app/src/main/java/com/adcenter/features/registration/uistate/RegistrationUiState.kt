package com.adcenter.features.registration.uistate

import com.adcenter.entities.view.AppConfigInfo

sealed class RegistrationUiState
object WaitRegistration : RegistrationUiState()
data class Success(val result: AppConfigInfo) : RegistrationUiState()
data class Error(val throwable: Throwable) : RegistrationUiState()