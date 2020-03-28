package com.adcenter.di.dagger.components

import com.adcenter.app.App
import com.adcenter.data.NetworkDataRequest
import com.adcenter.di.dagger.module.*
import com.adcenter.ui.activities.BaseActivity
import com.adcenter.ui.activities.DevSettingsActivity
import com.adcenter.ui.activities.MainActivity
import com.adcenter.ui.bottomsheet.NavigationBottomSheetDialogFragment
import com.adcenter.ui.fragments.SettingsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class,
        AppModule::class,
        NetworkModule::class,
        DataModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun lastAdsComponent(): LastAdsComponent
    fun adRequestsComponent(): AdRequestsComponent
    fun searchComponent(): SearchComponent
    fun bookmarksComponent(): BookmarksComponent
    fun myAdsComponent(): MyAdsComponent
    fun detailsComponent(): DetailsComponent
    fun newDetailsComponent(): NewDetailsComponent
    fun loginComponent(): LoginComponent
    fun registrationComponent(): RegistrationComponent

    fun inject(app: App)

    fun inject(activity: BaseActivity)
    fun inject(activity: MainActivity)
    fun inject(activity: DevSettingsActivity)

    fun inject(fragment: SettingsFragment)
    fun inject(fragment: NavigationBottomSheetDialogFragment)

    fun inject(p: NetworkDataRequest)
}