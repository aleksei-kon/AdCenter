package com.adcenter.features.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.config.IAppConfig
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.extensions.async
import com.adcenter.features.login.data.LoginRequestParams
import com.adcenter.features.login.uistate.LoginUiState
import com.adcenter.features.login.usecase.ILoginUseCase
import com.adcenter.utils.Result
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class LoginViewModel() : ViewModel() {

    @Inject
    lateinit var loginUseCase: ILoginUseCase

    @Inject
    lateinit var appConfig: IAppConfig

    init {
        Injector.plusLoginComponent().inject(this)
    }

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
            loginUiMutableState.value = LoginUiState.Success(model)
        }

        override fun onError(e: Throwable) {
            loginUiMutableState.value = LoginUiState.Error(e)
        }
    }

    private val loginUiMutableState = MutableLiveData<LoginUiState>()

    val loginData: LiveData<LoginUiState>
        get() = loginUiMutableState

    fun login(params: LoginRequestParams) {
        loginUiMutableState.value = LoginUiState.WaitLogin
        currentParams = params

        disposable?.dispose()

        dataSource
            .async()
            .subscribe(observer)
    }

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
        Injector.clearLoginComponent()
    }
}