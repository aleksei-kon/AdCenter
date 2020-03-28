package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.annotations.FragmentScope
import com.adcenter.di.dagger.module.AdRequestsModule
import com.adcenter.ui.fragments.AdRequestsFragment
import dagger.Subcomponent

@Subcomponent(modules = [AdRequestsModule::class])
@FragmentScope
interface AdRequestsComponent {

    fun inject(fragment: AdRequestsFragment)
}