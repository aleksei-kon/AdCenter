package com.adcenter.features.registration.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.app.config.AppConfig
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.registration.data.RegistrationRequestParams
import com.adcenter.features.registration.uistate.RegistrationUiState
import com.adcenter.features.registration.usecase.IRegistrationUseCase
import com.adcenter.utils.Result
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RegistrationViewModel(
    private val registrationUseCase: IRegistrationUseCase,
    private val gson: Gson
) : ViewModel(), CoroutineScope {

    private var registrationModel: AppConfigInfo = AppConfigInfo()
    private var currentParams: RegistrationRequestParams = RegistrationRequestParams()

    private val coroutineScopeJob = Job()
    private val registrationUiMutableState = MutableLiveData<RegistrationUiState>()

    val registrationData: LiveData<RegistrationUiState>
        get() = registrationUiMutableState

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun register(params: RegistrationRequestParams) {
        registrationUiMutableState.value = RegistrationUiState.WaitRegistration
        currentParams = params
        uploadModel()
    }

    private fun uploadModel() {
        coroutineContext.cancelChildren()

        launch {
            val json = gson.toJson(currentParams)

            when (val result = registrationUseCase.register(json)) {
                is Result.Success -> {
                    registrationModel = result.value
                    AppConfig.updateConfig(registrationModel)
                    registrationUiMutableState.value =
                        RegistrationUiState.Success(registrationModel)
                }
                is Result.Error -> {
                    registrationUiMutableState.value = RegistrationUiState.Error(result.exception)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}