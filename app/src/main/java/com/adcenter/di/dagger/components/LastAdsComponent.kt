package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.annotations.FragmentScope
import com.adcenter.di.dagger.module.LastAdsModule
import com.adcenter.ui.fragments.LastAdsFragment
import dagger.Subcomponent

@Subcomponent(modules = [LastAdsModule::class])
@FragmentScope
interface LastAdsComponent {

    fun inject(fragment: LastAdsFragment)
}
