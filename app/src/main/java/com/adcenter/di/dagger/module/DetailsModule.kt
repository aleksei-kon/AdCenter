package com.adcenter.di.dagger.module

import com.adcenter.api.IApi
import com.adcenter.data.processors.DetailsProcessor
import com.adcenter.di.dagger.scopes.DetailsScope
import com.adcenter.features.details.repository.DetailsRepository
import com.adcenter.features.details.repository.IDetailsRepository
import com.adcenter.features.details.usecase.DetailsUseCase
import com.adcenter.features.details.usecase.IDetailsUseCase
import dagger.Module
import dagger.Provides

@Module
class DetailsModule {

    @Provides
    @DetailsScope
    fun provideDetailsRepository(processor: DetailsProcessor, api: IApi): IDetailsRepository =
        DetailsRepository(processor, api)

    @Provides
    @DetailsScope
    fun provideDetailsUseCase(repository: IDetailsRepository): IDetailsUseCase =
        DetailsUseCase(repository)
}