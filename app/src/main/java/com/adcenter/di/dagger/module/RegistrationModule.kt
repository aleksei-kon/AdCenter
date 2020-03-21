package com.adcenter.di.dagger.module

import com.adcenter.api.IApi
import com.adcenter.data.processors.AppConfigProcessor
import com.adcenter.di.dagger.scopes.RegistrationScope
import com.adcenter.features.registration.repository.IRegistrationRepository
import com.adcenter.features.registration.repository.RegistrationRepository
import com.adcenter.features.registration.usecase.IRegistrationUseCase
import com.adcenter.features.registration.usecase.RegistrationUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class RegistrationModule {

    @Provides
    @RegistrationScope
    fun provideRegistrationRepository(
        processor: AppConfigProcessor,
        gson: Gson,
        api: IApi
    ): IRegistrationRepository =
        RegistrationRepository(processor, gson, api)

    @Provides
    @RegistrationScope
    fun provideRegistrationUseCase(repository: IRegistrationRepository): IRegistrationUseCase =
        RegistrationUseCase(repository)
}