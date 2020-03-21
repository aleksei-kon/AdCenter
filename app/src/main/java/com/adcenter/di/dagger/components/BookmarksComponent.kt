package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.module.BookmarksModule
import com.adcenter.di.dagger.scopes.BookmarksScope
import com.adcenter.features.bookmarks.viewmodel.BookmarksViewModel
import dagger.Subcomponent

@Subcomponent(modules = [BookmarksModule::class])
@BookmarksScope
interface BookmarksComponent {

    fun inject(viewModel: BookmarksViewModel)
}