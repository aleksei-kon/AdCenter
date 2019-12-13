package com.adcenter.koin.dependencies

import com.adcenter.features.details.repository.DetailsRepository
import com.adcenter.features.details.repository.IDetailsRepository
import com.adcenter.features.details.usecase.DetailsUseCase
import com.adcenter.features.details.usecase.IDetailsUseCase
import com.adcenter.features.details.viewmodel.DetailsViewModel
import com.adcenter.ui.activities.DetailsActivity
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DetailsDependency {
    val module = module {
        scope(named<DetailsActivity>()) {

            scoped<IDetailsRepository> { DetailsRepository() }

            scoped<IDetailsUseCase> {
                DetailsUseCase(
                    repository = get()
                )
            }

            viewModel {
                DetailsViewModel(
                    detailsUseCase = get()
                )
            }
        }
    }
}