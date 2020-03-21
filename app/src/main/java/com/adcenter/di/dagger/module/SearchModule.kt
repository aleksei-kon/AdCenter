package com.adcenter.di.dagger.module

import com.adcenter.api.IApi
import com.adcenter.data.processors.AdsDataProcessor
import com.adcenter.di.dagger.scopes.SearchScope
import com.adcenter.features.search.repository.ISearchRepository
import com.adcenter.features.search.repository.SearchRepository
import com.adcenter.features.search.usecase.ISearchUseCase
import com.adcenter.features.search.usecase.SearchUseCase
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @Provides
    @SearchScope
    fun provideSearchRepository(processor: AdsDataProcessor, api: IApi): ISearchRepository =
        SearchRepository(processor, api)

    @Provides
    @SearchScope
    fun provideSearchUseCase(repository: ISearchRepository): ISearchUseCase =
        SearchUseCase(repository)
}