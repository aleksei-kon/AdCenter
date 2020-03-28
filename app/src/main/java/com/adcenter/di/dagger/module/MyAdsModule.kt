package com.adcenter.di.dagger.module

import androidx.lifecycle.ViewModel
import com.adcenter.api.IApi
import com.adcenter.data.processors.AdsDataProcessor
import com.adcenter.di.dagger.annotations.FragmentScope
import com.adcenter.di.dagger.annotations.ViewModelKey
import com.adcenter.features.myads.repository.IMyAdsRepository
import com.adcenter.features.myads.repository.MyAdsRepository
import com.adcenter.features.myads.usecase.IMyAdsUseCase
import com.adcenter.features.myads.usecase.MyAdsUseCase
import com.adcenter.features.myads.viewmodel.MyAdsViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class MyAdsModule {

    @Provides
    @FragmentScope
    fun provideMyAdsRepository(processor: AdsDataProcessor, api: IApi): IMyAdsRepository =
        MyAdsRepository(processor, api)

    @Provides
    @FragmentScope
    fun provideMyAdsUseCase(repository: IMyAdsRepository): IMyAdsUseCase =
        MyAdsUseCase(repository)

    @Provides
    @IntoMap
    @FragmentScope
    @ViewModelKey(MyAdsViewModel::class)
    fun provideMyAdsViewModel(useCase: IMyAdsUseCase): ViewModel =
        MyAdsViewModel(useCase)
}