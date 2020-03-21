package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.module.AdRequestsModule
import com.adcenter.di.dagger.scopes.AdRequestsScope
import com.adcenter.features.adrequests.viewmodel.AdRequestsViewModel
import dagger.Subcomponent

@Subcomponent(modules = [AdRequestsModule::class])
@AdRequestsScope
interface AdRequestsComponent {

    fun inject(viewModel: AdRequestsViewModel)
}