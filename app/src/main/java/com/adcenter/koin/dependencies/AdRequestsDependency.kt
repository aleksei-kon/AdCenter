package com.adcenter.koin.dependencies

import com.adcenter.features.adrequests.repository.AdRequestsRepository
import com.adcenter.features.adrequests.repository.IAdRequestsRepository
import com.adcenter.features.adrequests.usecase.AdRequestsUseCase
import com.adcenter.features.adrequests.usecase.IAdRequestsUseCase
import com.adcenter.features.adrequests.viewmodel.AdRequestsViewModel
import com.adcenter.ui.fragments.AdRequestsFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.ScopeID
import org.koin.dsl.module

val adRequestsDependencyModule = module {
    scope(named<AdRequestsFragment>()) {
        scoped<IAdRequestsRepository> {
            AdRequestsRepository(
                processor = get()
            )
        }
        scoped<IAdRequestsUseCase> {
            AdRequestsUseCase(
                repository = get()
            )
        }
    }

    viewModel { (scopeId: ScopeID) ->
        AdRequestsViewModel(
            adRequestsUseCase = getScope(scopeId).get()
        )
    }
}