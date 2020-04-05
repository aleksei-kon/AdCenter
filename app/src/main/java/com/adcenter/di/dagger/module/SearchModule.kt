package com.adcenter.di.dagger.module

import androidx.lifecycle.ViewModel
import com.adcenter.datasource.mappers.AdsMapper
import com.adcenter.datasource.network.AdvertService
import com.adcenter.di.dagger.annotations.ActivityScope
import com.adcenter.di.dagger.annotations.ViewModelKey
import com.adcenter.features.search.repository.ISearchRepository
import com.adcenter.features.search.repository.SearchRepository
import com.adcenter.features.search.usecase.ISearchUseCase
import com.adcenter.features.search.usecase.SearchUseCase
import com.adcenter.features.search.viewmodel.SearchViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class SearchModule {

    @Provides
    @ActivityScope
    fun provideSearchRepository(
        advertService: AdvertService,
        adsMapper: AdsMapper
    ): ISearchRepository = SearchRepository(advertService, adsMapper)

    @Provides
    @ActivityScope
    fun provideSearchUseCase(repository: ISearchRepository): ISearchUseCase =
        SearchUseCase(repository)

    @Provides
    @IntoMap
    @ActivityScope
    @ViewModelKey(SearchViewModel::class)
    fun provideSearchViewModel(useCase: ISearchUseCase): ViewModel =
        SearchViewModel(useCase)
}