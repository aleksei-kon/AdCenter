package com.adcenter.di.dagger.module

import com.adcenter.api.IApi
import com.adcenter.data.processors.AdsDataProcessor
import com.adcenter.di.dagger.scopes.AdRequestsScope
import com.adcenter.features.adrequests.repository.AdRequestsRepository
import com.adcenter.features.adrequests.repository.IAdRequestsRepository
import com.adcenter.features.adrequests.usecase.AdRequestsUseCase
import com.adcenter.features.adrequests.usecase.IAdRequestsUseCase
import dagger.Module
import dagger.Provides

@Module
class AdRequestsModule {

    @Provides
    @AdRequestsScope
    fun provideAdRequestsRepository(processor: AdsDataProcessor, api: IApi): IAdRequestsRepository =
        AdRequestsRepository(processor, api)

    @Provides
    @AdRequestsScope
    fun provideAdRequestsUseCase(repository: IAdRequestsRepository): IAdRequestsUseCase =
        AdRequestsUseCase(repository)
}