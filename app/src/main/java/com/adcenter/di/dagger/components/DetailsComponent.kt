package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.module.DetailsModule
import com.adcenter.di.dagger.scopes.DetailsScope
import com.adcenter.features.details.viewmodel.DetailsViewModel
import dagger.Subcomponent

@Subcomponent(modules = [DetailsModule::class])
@DetailsScope
interface DetailsComponent {

    fun inject(viewModel: DetailsViewModel)
}