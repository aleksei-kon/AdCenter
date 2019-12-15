package com.adcenter.koin.dependencies

import com.adcenter.features.search.repository.ISearchRepository
import com.adcenter.features.search.repository.SearchRepository
import com.adcenter.features.search.usecase.ISearchUseCase
import com.adcenter.features.search.usecase.SearchUseCase
import com.adcenter.features.search.viewmodel.SearchViewModel
import com.adcenter.ui.fragments.SearchFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object SearchDependency {
    val module = module {
        scope(named<SearchFragment>()) {
            scoped<ISearchRepository> {
                SearchRepository(
                    processor = get()
                )
            }
            scoped<ISearchUseCase> {
                SearchUseCase(
                    repository = get()
                )
            }
            viewModel {
                SearchViewModel(
                    searchUseCase = get()
                )
            }
        }
    }
}