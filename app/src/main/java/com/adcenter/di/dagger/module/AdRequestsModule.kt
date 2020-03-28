package com.adcenter.di.dagger.module

import androidx.lifecycle.ViewModel
import com.adcenter.api.IApi
import com.adcenter.data.processors.AdsDataProcessor
import com.adcenter.di.dagger.annotations.FragmentScope
import com.adcenter.di.dagger.annotations.ViewModelKey
import com.adcenter.features.adrequests.repository.AdRequestsRepository
import com.adcenter.features.adrequests.repository.IAdRequestsRepository
import com.adcenter.features.adrequests.usecase.AdRequestsUseCase
import com.adcenter.features.adrequests.usecase.IAdRequestsUseCase
import com.adcenter.features.adrequests.viewmodel.AdRequestsViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class AdRequestsModule {

    @Provides
    @FragmentScope
    fun provideAdRequestsRepository(processor: AdsDataProcessor, api: IApi): IAdRequestsRepository =
        AdRequestsRepository(processor, api)

    @Provides
    @FragmentScope
    fun provideAdRequestsUseCase(repository: IAdRequestsRepository): IAdRequestsUseCase =
        AdRequestsUseCase(repository)

    @Provides
    @IntoMap
    @FragmentScope
    @ViewModelKey(AdRequestsViewModel::class)
    fun provideAdRequestsViewModel(useCase: IAdRequestsUseCase): ViewModel =
        AdRequestsViewModel(useCase)
}