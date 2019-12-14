package com.adcenter.koin.dependencies

import com.adcenter.features.lastads.repository.ILastAdsRepository
import com.adcenter.features.lastads.repository.LastAdsRepository
import com.adcenter.features.lastads.usecase.ILastAdsUseCase
import com.adcenter.features.lastads.usecase.LastAdsUseCase
import com.adcenter.features.lastads.viewmodel.LastAdsViewModel
import com.adcenter.ui.fragments.LastAdsFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object LastAdsDependency {
    val module = module {
        scope(named<LastAdsFragment>()) {
            scoped<ILastAdsRepository> { LastAdsRepository() }
            scoped<ILastAdsUseCase> {
                LastAdsUseCase(
                    repository = get()
                )
            }
            viewModel {
                LastAdsViewModel(
                    lastAdsUseCase = get()
                )
            }
        }
    }
}