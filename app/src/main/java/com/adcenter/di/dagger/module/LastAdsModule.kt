package com.adcenter.di.dagger.module

import com.adcenter.api.IApi
import com.adcenter.data.processors.AdsDataProcessor
import com.adcenter.di.dagger.scopes.LastAdsScope
import com.adcenter.features.lastads.repository.ILastAdsRepository
import com.adcenter.features.lastads.repository.LastAdsRepository
import com.adcenter.features.lastads.usecase.ILastAdsUseCase
import com.adcenter.features.lastads.usecase.LastAdsUseCase
import dagger.Module
import dagger.Provides

@Module
class LastAdsModule {

    @Provides
    @LastAdsScope
    fun provideLastAdsRepository(processor: AdsDataProcessor, api: IApi): ILastAdsRepository =
        LastAdsRepository(processor, api)

    @Provides
    @LastAdsScope
    fun provideLastAdsUseCase(repository: ILastAdsRepository): ILastAdsUseCase =
        LastAdsUseCase(repository)
}