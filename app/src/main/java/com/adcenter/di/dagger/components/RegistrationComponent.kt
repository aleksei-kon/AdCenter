package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.module.RegistrationModule
import com.adcenter.di.dagger.scopes.RegistrationScope
import com.adcenter.features.registration.viewmodel.RegistrationViewModel
import dagger.Subcomponent

@Subcomponent(modules = [RegistrationModule::class])
@RegistrationScope
interface RegistrationComponent {

    fun inject(viewModel: RegistrationViewModel)
}