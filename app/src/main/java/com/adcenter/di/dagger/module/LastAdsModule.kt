package com.adcenter.di.dagger.module

import androidx.lifecycle.ViewModel
import com.adcenter.datasource.database.AdvertsDao
import com.adcenter.datasource.mappers.AdvertsMapper
import com.adcenter.datasource.network.AdvertsService
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
    fun provideLastAdsRepository(
        advertsService: AdvertsService,
        advertsDao: AdvertsDao,
        advertsMapper: AdvertsMapper
    ): ILastAdsRepository = LastAdsRepository(advertsService, advertsDao, advertsMapper)

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