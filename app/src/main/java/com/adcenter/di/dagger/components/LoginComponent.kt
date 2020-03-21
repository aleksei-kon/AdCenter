package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.module.LoginModule
import com.adcenter.di.dagger.scopes.LoginScope
import com.adcenter.features.login.viewmodel.LoginViewModel
import dagger.Subcomponent

@Subcomponent(modules = [LoginModule::class])
@LoginScope
interface LoginComponent {

    fun inject(viewModel: LoginViewModel)
}