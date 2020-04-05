package com.adcenter.di.dagger.module

import androidx.lifecycle.ViewModel
import com.adcenter.datasource.mappers.DetailsMapper
import com.adcenter.datasource.network.AdvertService
import com.adcenter.di.dagger.annotations.ActivityScope
import com.adcenter.di.dagger.annotations.ViewModelKey
import com.adcenter.features.details.repository.DetailsRepository
import com.adcenter.features.details.repository.IDetailsRepository
import com.adcenter.features.details.usecase.DetailsUseCase
import com.adcenter.features.details.usecase.IDetailsUseCase
import com.adcenter.features.details.viewmodel.DetailsViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class DetailsModule {

    @Provides
    @ActivityScope
    fun provideDetailsRepository(
        advertService: AdvertService,
        detailsMapper: DetailsMapper
    ): IDetailsRepository = DetailsRepository(advertService, detailsMapper)

    @Provides
    @ActivityScope
    fun provideDetailsUseCase(repository: IDetailsRepository): IDetailsUseCase =
        DetailsUseCase(repository)

    @Provides
    @IntoMap
    @ActivityScope
    @ViewModelKey(DetailsViewModel::class)
    fun provideDetailsViewModel(useCase: IDetailsUseCase): ViewModel =
        DetailsViewModel(useCase)
}