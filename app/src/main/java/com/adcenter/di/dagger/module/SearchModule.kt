package com.adcenter.di.dagger.module

import androidx.lifecycle.ViewModel
import com.adcenter.datasource.database.AdvertsDao
import com.adcenter.datasource.mappers.AdvertsMapper
import com.adcenter.datasource.network.AdvertsService
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
        advertsService: AdvertsService,
        advertsDao: AdvertsDao,
        advertsMapper: AdvertsMapper
    ): ISearchRepository = SearchRepository(advertsService, advertsDao, advertsMapper)

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