package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.annotations.ActivityScope
import com.adcenter.di.dagger.module.SearchModule
import com.adcenter.ui.activities.SearchActivity
import dagger.Subcomponent

@Subcomponent(modules = [SearchModule::class])
@ActivityScope
interface SearchComponent {

    fun inject(activity: SearchActivity)
}