package com.adcenter.di.dagger.module

import androidx.lifecycle.ViewModel
import com.adcenter.datasource.api.IApi
import com.adcenter.datasource.processors.AdsDataProcessor
import com.adcenter.di.dagger.annotations.FragmentScope
import com.adcenter.di.dagger.annotations.ViewModelKey
import com.adcenter.features.lastads.repository.ILastAdsRepository
import com.adcenter.features.lastads.repository.LastAdsRepository
import com.adcenter.features.lastads.usecase.ILastAdsUseCase
import com.adcenter.features.lastads.usecase.LastAdsUseCase
import com.adcenter.features.lastads.viewmodel.LastAdsViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class LastAdsModule {

    @Provides
    @FragmentScope
    fun provideLastAdsRepository(processor: AdsDataProcessor, api: IApi): ILastAdsRepository =
        LastAdsRepository(processor, api)

    @Provides
    @FragmentScope
    fun provideLastAdsUseCase(repository: ILastAdsRepository): ILastAdsUseCase =
        LastAdsUseCase(repository)

    @Provides
    @IntoMap
    @FragmentScope
    @ViewModelKey(LastAdsViewModel::class)
    fun provideLastAdsViewModel(useCase: ILastAdsUseCase): ViewModel =
        LastAdsViewModel(useCase)
}