package com.adcenter.features.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.appconfig.IAppConfig
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.extensions.async
import com.adcenter.features.login.models.LoginRequestParams
import com.adcenter.features.login.uistate.LoginUiState
import com.adcenter.features.login.usecase.ILoginUseCase
import com.adcenter.entities.Result
import com.adcenter.features.login.uistate.Error
import com.adcenter.features.login.uistate.Success
import com.adcenter.features.login.uistate.WaitLogin
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class LoginViewModel(
    private val appConfig: IAppConfig,
    private val loginUseCase: ILoginUseCase
) : ViewModel() {

    private var loginModel: AppConfigInfo = AppConfigInfo()
    private var currentParams: LoginRequestParams = LoginRequestParams()
    private var disposable: Disposable? = null

    private val dataSource: Single<AppConfigInfo>
        get() = Single.create {
            when (val result = loginUseCase.login(currentParams)) {
                is Result.Success -> {
                    loginModel = result.value
                    appConfig.updateConfig(loginModel)
                    it.onSuccess(loginModel)
                }
                is Result.Error -> it.onError(result.exception)
            }
        }

    private val observer = object : SingleObserver<AppConfigInfo> {

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onSuccess(model: AppConfigInfo) {
            loginUiMutableState.value = Success(model)
        }

        override fun onError(e: Throwable) {
            loginUiMutableState.value = Error(e)
        }
    }

    private val loginUiMutableState = MutableLiveData<LoginUiState>()

    val loginData: LiveData<LoginUiState>
        get() = loginUiMutableState

    fun login(params: LoginRequestParams) {
        loginUiMutableState.value = WaitLogin
        currentParams = params

        disposable?.dispose()

        dataSource
            .async()
            .subscribe(observer)
    }

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
    }
}