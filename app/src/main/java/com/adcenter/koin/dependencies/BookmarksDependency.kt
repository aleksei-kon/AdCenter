package com.adcenter.koin.dependencies

import com.adcenter.features.bookmarks.repository.BookmarksRepository
import com.adcenter.features.bookmarks.repository.IBookmarksRepository
import com.adcenter.features.bookmarks.usecase.BookmarksUseCase
import com.adcenter.features.bookmarks.usecase.IBookmarksUseCase
import com.adcenter.features.bookmarks.viewmodel.BookmarksViewModel
import com.adcenter.ui.fragments.BookmarksFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.ScopeID
import org.koin.dsl.module

object BookmarksDependency {
    val module = module {
        scope(named<BookmarksFragment>()) {
            scoped<IBookmarksRepository> {
                BookmarksRepository(
                    processor = get()
                )
            }
            scoped<IBookmarksUseCase> {
                BookmarksUseCase(
                    repository = get()
                )
            }
        }

        viewModel { (scopeId: ScopeID) ->
            BookmarksViewModel(
                bookmarksUseCase = getScope(scopeId).get()
            )
        }
    }
}