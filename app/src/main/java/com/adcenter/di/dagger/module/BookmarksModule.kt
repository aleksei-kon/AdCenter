package com.adcenter.di.dagger.module

import androidx.lifecycle.ViewModel
import com.adcenter.datasource.database.AdvertsDao
import com.adcenter.datasource.mappers.AdvertsMapper
import com.adcenter.datasource.network.AdvertsService
import com.adcenter.di.dagger.annotations.FragmentScope
import com.adcenter.di.dagger.annotations.ViewModelKey
import com.adcenter.features.bookmarks.repository.BookmarksRepository
import com.adcenter.features.bookmarks.repository.IBookmarksRepository
import com.adcenter.features.bookmarks.usecase.BookmarksUseCase
import com.adcenter.features.bookmarks.usecase.IBookmarksUseCase
import com.adcenter.features.bookmarks.viewmodel.BookmarksViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class BookmarksModule {

    @Provides
    @FragmentScope
    fun provideBookmarksRepository(
        advertsService: AdvertsService,
        advertsDao: AdvertsDao,
        advertsMapper: AdvertsMapper
    ): IBookmarksRepository = BookmarksRepository(advertsService, advertsDao, advertsMapper)

    @Provides
    @FragmentScope
    fun provideBookmarksUseCase(repository: IBookmarksRepository): IBookmarksUseCase =
        BookmarksUseCase(repository)

    @Provides
    @IntoMap
    @FragmentScope
    @ViewModelKey(BookmarksViewModel::class)
    fun provideBookmarksViewModel(useCase: IBookmarksUseCase): ViewModel =
        BookmarksViewModel(useCase)
}