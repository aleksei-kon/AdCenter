package com.adcenter.di.dagger.module

import com.adcenter.api.IApi
import com.adcenter.data.processors.AppConfigProcessor
import com.adcenter.di.dagger.scopes.LoginScope
import com.adcenter.features.login.repository.ILoginRepository
import com.adcenter.features.login.repository.LoginRepository
import com.adcenter.features.login.usecase.ILoginUseCase
import com.adcenter.features.login.usecase.LoginUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

    @Provides
    @LoginScope
    fun provideLoginRepository(
        processor: AppConfigProcessor,
        gson: Gson,
        api: IApi
    ): ILoginRepository =
        LoginRepository(processor, gson, api)

    @Provides
    @LoginScope
    fun provideLoginUseCase(repository: ILoginRepository): ILoginUseCase =
        LoginUseCase(repository)
}