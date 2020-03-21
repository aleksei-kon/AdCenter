package com.adcenter.features.registration.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.config.IAppConfig
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.extensions.async
import com.adcenter.features.registration.data.RegistrationRequestParams
import com.adcenter.features.registration.uistate.RegistrationUiState
import com.adcenter.features.registration.usecase.IRegistrationUseCase
import com.adcenter.utils.Result
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class RegistrationViewModel : ViewModel() {

    @Inject
    lateinit var registrationUseCase: IRegistrationUseCase

    @Inject
    lateinit var appConfig: IAppConfig

    init {
        Injector.plusRegistrationComponent().inject(this)
    }

    private var registrationModel: AppConfigInfo = AppConfigInfo()
    private var currentParams: RegistrationRequestParams = RegistrationRequestParams()
    private var disposable: Disposable? = null

    private val dataSource: Single<AppConfigInfo>
        get() = Single.create {
            when (val result = registrationUseCase.register(currentParams)) {
                is Result.Success -> {
                    registrationModel = result.value
                    appConfig.updateConfig(registrationModel)
                    it.onSuccess(registrationModel)
                }
                is Result.Error -> it.onError(result.exception)
            }
        }

    private val observer = object : SingleObserver<AppConfigInfo> {

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onSuccess(model: AppConfigInfo) {
            registrationUiMutableState.value = RegistrationUiState.Success(model)
        }

        override fun onError(e: Throwable) {
            registrationUiMutableState.value = RegistrationUiState.Error(e)
        }
    }

    private val registrationUiMutableState = MutableLiveData<RegistrationUiState>()

    val registrationData: LiveData<RegistrationUiState>
        get() = registrationUiMutableState

    fun register(params: RegistrationRequestParams) {
        registrationUiMutableState.value = RegistrationUiState.WaitRegistration
        currentParams = params

        disposable?.dispose()

        dataSource
            .async()
            .subscribe(observer)
    }

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
        Injector.clearRegistrationComponent()
    }
}