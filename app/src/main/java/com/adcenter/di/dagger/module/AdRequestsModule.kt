package com.adcenter.di.dagger.module

import androidx.lifecycle.ViewModel
import com.adcenter.datasource.mappers.AdsMapper
import com.adcenter.datasource.network.AdvertService
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
    fun provideAdRequestsRepository(
        advertService: AdvertService,
        adsMapper: AdsMapper
    ): IAdRequestsRepository = AdRequestsRepository(advertService, adsMapper)

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