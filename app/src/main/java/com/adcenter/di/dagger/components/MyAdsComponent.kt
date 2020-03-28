package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.annotations.FragmentScope
import com.adcenter.di.dagger.module.MyAdsModule
import com.adcenter.ui.fragments.MyAdsFragment
import dagger.Subcomponent

@Subcomponent(modules = [MyAdsModule::class])
@FragmentScope
interface MyAdsComponent {

    fun inject(fragment: MyAdsFragment)
}