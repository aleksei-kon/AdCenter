package com.adcenter.di.dagger.components

import android.content.Context
import com.adcenter.di.dagger.module.AppModule
import com.adcenter.di.dagger.module.MappersModule
import com.adcenter.di.dagger.module.NetworkModule
import com.adcenter.di.dagger.module.ViewModelModule
import com.adcenter.ui.activities.BaseActivity
import com.adcenter.ui.activities.DevSettingsActivity
import com.adcenter.ui.activities.MainActivity
import com.adcenter.ui.activities.SplashActivity
import com.adcenter.ui.bottomsheet.NavigationBottomSheetDialogFragment
import com.adcenter.ui.fragments.SettingsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        MappersModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder
    }

    fun lastAdsComponent(): LastAdsComponent
    fun adRequestsComponent(): AdRequestsComponent
    fun searchComponent(): SearchComponent
    fun bookmarksComponent(): BookmarksComponent
    fun myAdsComponent(): MyAdsComponent
    fun detailsComponent(): DetailsComponent
    fun newDetailsComponent(): NewDetailsComponent
    fun loginComponent(): LoginComponent
    fun registrationComponent(): RegistrationComponent

    fun inject(activity: SplashActivity)
    fun inject(activity: BaseActivity)
    fun inject(activity: MainActivity)
    fun inject(activity: DevSettingsActivity)
    fun inject(fragment: SettingsFragment)
    fun inject(fragment: NavigationBottomSheetDialogFragment)
}