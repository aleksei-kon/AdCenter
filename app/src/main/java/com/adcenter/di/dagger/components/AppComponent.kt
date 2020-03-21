package com.adcenter.di.dagger.components

import com.adcenter.app.App
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.processors.*
import com.adcenter.di.dagger.module.AppModule
import com.adcenter.di.dagger.module.ContextModule
import com.adcenter.di.dagger.module.LastAdsModule
import com.adcenter.di.dagger.module.NetworkModule
import com.adcenter.features.adrequests.repository.AdRequestsRepository
import com.adcenter.features.bookmarks.repository.BookmarksRepository
import com.adcenter.features.details.repository.DetailsRepository
import com.adcenter.features.login.repository.LoginRepository
import com.adcenter.features.login.viewmodel.LoginViewModel
import com.adcenter.features.myads.repository.MyAdsRepository
import com.adcenter.features.newdetails.repository.NewDetailsRepository
import com.adcenter.features.newdetails.repository.PhotoRepository
import com.adcenter.features.registration.repository.RegistrationRepository
import com.adcenter.features.registration.viewmodel.RegistrationViewModel
import com.adcenter.features.search.repository.SearchRepository
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
        NetworkModule::class
    ]
)
interface AppComponent {

    fun plusLastAdsComponent(module: LastAdsModule): LastAdsComponent

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
    fun inject(component: LoginRepository)
    fun inject(component: DetailsProcessor)
    fun inject(component: ShowHideProcessor)
    fun inject(component: AppConfigProcessor)
    fun inject(component: NewDetailsProcessor)
    fun inject(component: PhotoProcessor)
    fun inject(component: RegistrationRepository)
    fun inject(component: NewDetailsRepository)
    fun inject(component: AdRequestsRepository)
    fun inject(component: BookmarksRepository)
    fun inject(component: DetailsRepository)
    fun inject(component: LoginViewModel)
    fun inject(component: MyAdsRepository)
    fun inject(component: PhotoRepository)
    fun inject(component: RegistrationViewModel)
    fun inject(component: SearchRepository)
    fun inject(component: ShowHideButtonController)
}