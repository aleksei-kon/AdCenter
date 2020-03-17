package com.adcenter.features.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.config.AppConfig
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.login.data.LoginRequestParams
import com.adcenter.features.login.uistate.LoginUiState
import com.adcenter.features.login.usecase.ILoginUseCase
import com.adcenter.utils.Result
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginViewModel(
    private val loginUseCase: ILoginUseCase,
    private val gson: Gson
) : ViewModel(), CoroutineScope {

    private var loginModel: AppConfigInfo = AppConfigInfo()
    private var currentParams: LoginRequestParams = LoginRequestParams()

    private val coroutineScopeJob = Job()
    private val loginUiMutableState = MutableLiveData<LoginUiState>()

    val loginData: LiveData<LoginUiState>
        get() = loginUiMutableState

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun login(params: LoginRequestParams) {
        loginUiMutableState.value = LoginUiState.WaitLogin
        currentParams = params
        uploadModel()
    }

    private fun uploadModel() {
        coroutineContext.cancelChildren()

        launch {
            val json = gson.toJson(currentParams)

            when (val result = loginUseCase.login(json)) {
                is Result.Success -> {
                    loginModel = result.value
                    AppConfig.updateConfig(loginModel)
                    loginUiMutableState.value = LoginUiState.Success(loginModel)
                }
                is Result.Error -> {
                    loginUiMutableState.value = LoginUiState.Error(result.exception)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}