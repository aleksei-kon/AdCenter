package com.adcenter.di.koin.dependencies

import com.adcenter.features.details.repository.DetailsRepository
import com.adcenter.features.details.repository.IDetailsRepository
import com.adcenter.features.details.usecase.DetailsUseCase
import com.adcenter.features.details.usecase.IDetailsUseCase
import com.adcenter.features.details.viewmodel.DetailsViewModel
import com.adcenter.ui.activities.DetailsActivity
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.ScopeID
import org.koin.dsl.module

val detailsDependencyModule = module {
    scope(named<DetailsActivity>()) {
        scoped<IDetailsRepository> {
            DetailsRepository(
                processor = get()
            )
        }
        scoped<IDetailsUseCase> {
            DetailsUseCase(
                repository = get()
            )
        }
    }

    viewModel { (scopeId: ScopeID) ->
        DetailsViewModel(
            detailsUseCase = getScope(scopeId).get()
        )
    }
}