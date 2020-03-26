package com.adcenter.di.dagger.components

import com.adcenter.app.App
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.processors.ShowHideProcessor
import com.adcenter.di.dagger.module.*
import com.adcenter.ui.activities.*
import com.adcenter.ui.bottomsheet.NavigationBottomSheetDialogFragment
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
    fun plusNewDetailsComponent(module: NewDetailsModule): NewDetailsComponent
    fun plusLoginComponent(module: LoginModule): LoginComponent
    fun plusRegistrationComponent(module: RegistrationModule): RegistrationComponent

    fun inject(app: App)

    fun inject(activity: BaseActivity)
    fun inject(activity: MainActivity)
    fun inject(activity: DevSettingsActivity)
    fun inject(activity: DetailsActivity)

    fun inject(fragment: SettingsFragment)
    fun inject(fragment: AdRequestsFragment)
    fun inject(fragment: BookmarksFragment)
    fun inject(fragment: LastAdsFragment)
    fun inject(fragment: MyAdsFragment)
    fun inject(fragment: NavigationBottomSheetDialogFragment)

    fun inject(p: NetworkDataRequest)
    fun inject(p: ShowHideProcessor)
    fun inject(p: ShowHideButtonController)
}