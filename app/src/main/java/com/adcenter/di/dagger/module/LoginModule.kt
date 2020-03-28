package com.adcenter.di.dagger.module

import androidx.lifecycle.ViewModel
import com.adcenter.api.IApi
import com.adcenter.config.IAppConfig
import com.adcenter.data.processors.AppConfigProcessor
import com.adcenter.di.dagger.annotations.ActivityScope
import com.adcenter.di.dagger.annotations.ViewModelKey
import com.adcenter.features.login.repository.ILoginRepository
import com.adcenter.features.login.repository.LoginRepository
import com.adcenter.features.login.usecase.ILoginUseCase
import com.adcenter.features.login.usecase.LoginUseCase
import com.adcenter.features.login.viewmodel.LoginViewModel
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class LoginModule {

    @Provides
    @ActivityScope
    fun provideLoginRepository(
        processor: AppConfigProcessor,
        gson: Gson,
        api: IApi
    ): ILoginRepository =
        LoginRepository(processor, gson, api)

    @Provides
    @ActivityScope
    fun provideLoginUseCase(repository: ILoginRepository): ILoginUseCase =
        LoginUseCase(repository)

    @Provides
    @IntoMap
    @ActivityScope
    @ViewModelKey(LoginViewModel::class)
    fun provideLoginViewModel(appConfig: IAppConfig, useCase: ILoginUseCase): ViewModel =
        LoginViewModel(appConfig, useCase)
}