package com.adcenter.di.dagger.module

import com.adcenter.api.IApi
import com.adcenter.data.processors.AdsDataProcessor
import com.adcenter.di.dagger.scopes.MyAdsScope
import com.adcenter.features.myads.repository.IMyAdsRepository
import com.adcenter.features.myads.repository.MyAdsRepository
import com.adcenter.features.myads.usecase.IMyAdsUseCase
import com.adcenter.features.myads.usecase.MyAdsUseCase
import dagger.Module
import dagger.Provides

@Module
class MyAdsModule {

    @Provides
    @MyAdsScope
    fun provideMyAdsRepository(processor: AdsDataProcessor, api: IApi): IMyAdsRepository =
        MyAdsRepository(processor, api)

    @Provides
    @MyAdsScope
    fun provideMyAdsUseCase(repository: IMyAdsRepository): IMyAdsUseCase =
        MyAdsUseCase(repository)
}