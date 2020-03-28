package com.adcenter.di.dagger.module

import androidx.lifecycle.ViewModel
import com.adcenter.api.IApi
import com.adcenter.data.processors.AdsDataProcessor
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
    fun provideBookmarksRepository(processor: AdsDataProcessor, api: IApi): IBookmarksRepository =
        BookmarksRepository(processor, api)

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