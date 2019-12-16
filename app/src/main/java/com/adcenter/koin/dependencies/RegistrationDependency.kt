package com.adcenter.koin.dependencies

import com.adcenter.features.registration.repository.IRegistrationRepository
import com.adcenter.features.registration.repository.RegistrationRepository
import com.adcenter.features.registration.usecase.IRegistrationUseCase
import com.adcenter.features.registration.usecase.RegistrationUseCase
import com.adcenter.features.registration.viewmodel.RegistrationViewModel
import com.adcenter.ui.activities.RegisterActivity
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object RegistrationDependency {
    val module = module {
        scope(named<RegisterActivity>()) {
            scoped<IRegistrationRepository> {
                RegistrationRepository(
                    processor = get()
                )
            }
            scoped<IRegistrationUseCase> {
                RegistrationUseCase(
                    repository = get()
                )
            }
            viewModel {
                RegistrationViewModel(
                    registrationUseCase = get(),
                    gson = get()
                )
            }
        }
    }
}