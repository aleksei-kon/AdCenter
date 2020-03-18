package com.adcenter.di.dagger.components

import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.processors.*
import com.adcenter.di.dagger.module.AppModule
import com.adcenter.di.dagger.module.ContextModule
import com.adcenter.di.dagger.module.NetworkModule
import com.adcenter.features.login.repository.LoginRepository
import com.adcenter.features.newdetails.repository.NewDetailsRepository
import com.adcenter.features.registration.repository.RegistrationRepository
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

    fun inject(component: NetworkDataRequest)
    fun inject(component: NavigationItem)
    fun inject(component: LoginRepository)
    fun inject(component: DetailsProcessor)
    fun inject(component: ShowHideProcessor)
    fun inject(component: AdsDataProcessor)
    fun inject(component: AppConfigProcessor)
    fun inject(component: NewDetailsProcessor)
    fun inject(component: PhotoProcessor)
    fun inject(component: RegistrationRepository)
    fun inject(component: NewDetailsRepository)
}