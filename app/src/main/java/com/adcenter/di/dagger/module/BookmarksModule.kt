package com.adcenter.di.dagger.module

import com.adcenter.api.IApi
import com.adcenter.data.processors.AdsDataProcessor
import com.adcenter.di.dagger.scopes.BookmarksScope
import com.adcenter.di.dagger.scopes.SearchScope
import com.adcenter.features.bookmarks.repository.BookmarksRepository
import com.adcenter.features.bookmarks.repository.IBookmarksRepository
import com.adcenter.features.bookmarks.usecase.BookmarksUseCase
import com.adcenter.features.bookmarks.usecase.IBookmarksUseCase
import dagger.Module
import dagger.Provides

@Module
class BookmarksModule {

    @Provides
    @BookmarksScope
    fun provideBookmarksRepository(processor: AdsDataProcessor, api: IApi): IBookmarksRepository =
        BookmarksRepository(processor, api)

    @Provides
    @BookmarksScope
    fun provideBookmarksUseCase(repository: IBookmarksRepository): IBookmarksUseCase =
        BookmarksUseCase(repository)
}