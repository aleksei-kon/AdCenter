package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.module.MyAdsModule
import com.adcenter.di.dagger.scopes.MyAdsScope
import com.adcenter.features.myads.viewmodel.MyAdsViewModel
import dagger.Subcomponent

@Subcomponent(modules = [MyAdsModule::class])
@MyAdsScope
interface MyAdsComponent {

    fun inject(viewModel: MyAdsViewModel)
}