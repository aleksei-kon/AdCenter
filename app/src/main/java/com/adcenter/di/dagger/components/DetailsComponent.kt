package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.annotations.ActivityScope
import com.adcenter.di.dagger.module.DetailsModule
import com.adcenter.ui.activities.DetailsActivity
import dagger.Subcomponent

@Subcomponent(modules = [DetailsModule::class])
@ActivityScope
interface DetailsComponent {

    fun inject(activity: DetailsActivity)
}