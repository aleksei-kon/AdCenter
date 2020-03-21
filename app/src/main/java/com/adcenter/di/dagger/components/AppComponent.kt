package com.adcenter.di.dagger.components

import com.adcenter.app.App
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.processors.DetailsProcessor
import com.adcenter.data.processors.NewDetailsProcessor
import com.adcenter.data.processors.PhotoProcessor
import com.adcenter.data.processors.ShowHideProcessor
import com.adcenter.di.dagger.module.*
import com.adcenter.features.newdetails.repository.NewDetailsRepository
import com.adcenter.features.newdetails.repository.PhotoRepository
import com.adcenter.ui.NavigationItem
import com.adcenter.ui.activities.BaseActivity
import com.adcenter.ui.activities.DetailsActivity
import com.adcenter.ui.activities.DevSettingsActivity
import com.adcenter.ui.activities.MainActivity
import com.adcenter.ui.controllers.ShowHideButtonController
import com.adcenter.ui.fragments.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class,
        AppModule::class,
        NetworkModule::class,
        DataModule::class
    ]
)
interface AppComponent {

    fun plusLastAdsComponent(module: LastAdsModule): LastAdsComponent
    fun plusAdRequestsComponent(module: AdRequestsModule): AdRequestsComponent
    fun plusSearchComponent(module: SearchModule): SearchComponent
    fun plusBookmarksComponent(module: BookmarksModule): BookmarksComponent
    fun plusMyAdsComponent(module: MyAdsModule): MyAdsComponent
    fun plusDetailsComponent(module: DetailsModule): DetailsComponent
    fun plusLoginComponent(module: LoginModule): LoginComponent
    fun plusRegistrationComponent(module: RegistrationModule): RegistrationComponent

    fun inject(app: App)

    fun inject(activity: BaseActivity)
    fun inject(activity: DevSettingsActivity)
    fun inject(activity: DetailsActivity)
    fun inject(activity: MainActivity)

    fun inject(fragment: SettingsFragment)
    fun inject(fragment: AdRequestsFragment)
    fun inject(fragment: BookmarksFragment)
    fun inject(fragment: LastAdsFragment)
    fun inject(fragment: MyAdsFragment)
    fun inject(fragment: SearchFragment)

    fun inject(component: NetworkDataRequest)
    fun inject(component: NavigationItem)
    fun inject(component: DetailsProcessor)
    fun inject(component: ShowHideProcessor)
    fun inject(component: NewDetailsProcessor)
    fun inject(component: PhotoProcessor)
    fun inject(component: NewDetailsRepository)
    fun inject(component: PhotoRepository)
    fun inject(component: ShowHideButtonController)
}