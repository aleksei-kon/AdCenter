package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.module.NewDetailsModule
import com.adcenter.di.dagger.scopes.NewDetailsScope
import com.adcenter.features.newdetails.viewmodel.NewDetailsViewModel
import dagger.Subcomponent

@Subcomponent(modules = [NewDetailsModule::class])
@NewDetailsScope
interface NewDetailsComponent {

    fun inject(viewModel: NewDetailsViewModel)
}