package com.adcenter.di.dagger.module

import androidx.lifecycle.ViewModel
import com.adcenter.appconfig.IAppConfig
import com.adcenter.datasource.mappers.AppConfigMapper
import com.adcenter.datasource.network.AccountService
import com.adcenter.di.dagger.annotations.ActivityScope
import com.adcenter.di.dagger.annotations.ViewModelKey
import com.adcenter.features.registration.repository.IRegistrationRepository
import com.adcenter.features.registration.repository.RegistrationRepository
import com.adcenter.features.registration.usecase.IRegistrationUseCase
import com.adcenter.features.registration.usecase.RegistrationUseCase
import com.adcenter.features.registration.viewmodel.RegistrationViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class RegistrationModule {

    @Provides
    @ActivityScope
    fun provideRegistrationRepository(
        accountService: AccountService,
        appConfigMapper: AppConfigMapper
    ): IRegistrationRepository = RegistrationRepository(accountService, appConfigMapper)

    @Provides
    @ActivityScope
    fun provideRegistrationUseCase(repository: IRegistrationRepository): IRegistrationUseCase =
        RegistrationUseCase(repository)

    @Provides
    @IntoMap
    @ActivityScope
    @ViewModelKey(RegistrationViewModel::class)
    fun provideRegistrationViewModel(
        appConfig: IAppConfig,
        useCase: IRegistrationUseCase
    ): ViewModel = RegistrationViewModel(appConfig, useCase)
}