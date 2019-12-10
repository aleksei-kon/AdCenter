package com.adcenter.koin.dependencies

import com.adcenter.bookmarks.repository.BookmarksRepository
import com.adcenter.bookmarks.repository.IBookmarksRepository
import com.adcenter.bookmarks.usecase.BookmarksUseCase
import com.adcenter.bookmarks.usecase.IBookmarksUseCase
import com.adcenter.bookmarks.viewmodel.BookmarksViewModel
import com.adcenter.ui.fragments.BookmarksFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object BookmarksDependency {
    val module = module {
        scope(named<BookmarksFragment>()) {

            scoped<IBookmarksRepository> { BookmarksRepository() }

            scoped<IBookmarksUseCase> {
                BookmarksUseCase(
                    repository = get()
                )
            }

            viewModel {
                BookmarksViewModel(
                    bookmarksUseCase = get()
                )
            }
        }
    }
}