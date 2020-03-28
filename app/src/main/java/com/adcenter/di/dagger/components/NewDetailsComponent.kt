package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.annotations.ActivityScope
import com.adcenter.di.dagger.module.NewDetailsModule
import com.adcenter.ui.activities.NewAdActivity
import dagger.Subcomponent

@Subcomponent(modules = [NewDetailsModule::class])
@ActivityScope
interface NewDetailsComponent {

    fun inject(activity: NewAdActivity)
}