package com.adcenter.di.dagger.components

import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.processors.DetailsProcessor
import com.adcenter.di.dagger.module.AppModule
import com.adcenter.di.dagger.module.ContextModule
import com.adcenter.di.dagger.module.NetworkModule
import com.adcenter.ui.NavigationItem
import com.adcenter.ui.activities.BaseActivity
import com.adcenter.ui.fragments.*
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ContextModule::class, AppModule::class, NetworkModule::class])
@Singleton
interface AppComponent {

    fun inject(activity: BaseActivity)
    fun inject(fragment: SettingsFragment)
    fun inject(fragment: AdRequestsFragment)
    fun inject(fragment: BookmarksFragment)
    fun inject(fragment: LastAdsFragment)
    fun inject(fragment: MyAdsFragment)
    fun inject(fragment: SearchFragment)
    fun inject(dataRequest: NetworkDataRequest)
    fun inject(item: NavigationItem)
    fun inject(processor: DetailsProcessor)
}