package com.adcenter.di.dagger.module

import androidx.lifecycle.ViewModel
import com.adcenter.api.IApi
import com.adcenter.data.processors.DetailsProcessor
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
    fun provideDetailsRepository(processor: DetailsProcessor, api: IApi): IDetailsRepository =
        DetailsRepository(processor, api)

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