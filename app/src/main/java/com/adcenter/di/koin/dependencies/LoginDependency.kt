package com.adcenter.di.koin.dependencies

import com.adcenter.features.login.repository.ILoginRepository
import com.adcenter.features.login.repository.LoginRepository
import com.adcenter.features.login.usecase.ILoginUseCase
import com.adcenter.features.login.usecase.LoginUseCase
import com.adcenter.features.login.viewmodel.LoginViewModel
import com.adcenter.ui.activities.LoginActivity
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.ScopeID
import org.koin.dsl.module

val loginDependencyModule = module {
    scope(named<LoginActivity>()) {
        scoped<ILoginRepository> {
            LoginRepository(
                processor = get(),
                gson = get()
            )
        }
        scoped<ILoginUseCase> {
            LoginUseCase(
                repository = get()
            )
        }
    }

    viewModel { (scopeId: ScopeID) ->
        LoginViewModel(
            loginUseCase = getScope(scopeId).get()
        )
    }
}