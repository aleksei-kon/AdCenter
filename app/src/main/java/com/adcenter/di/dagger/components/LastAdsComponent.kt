package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.module.LastAdsModule
import com.adcenter.di.dagger.scopes.LastAdsScope
import com.adcenter.features.lastads.viewmodel.LastAdsViewModel
import dagger.Subcomponent

@Subcomponent(modules = [LastAdsModule::class])
@LastAdsScope
interface LastAdsComponent {

    fun inject(viewModel: LastAdsViewModel)
}
