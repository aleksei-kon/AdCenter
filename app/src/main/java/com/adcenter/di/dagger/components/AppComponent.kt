package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.module.AppModule
import com.adcenter.di.dagger.module.ContextModule
import com.adcenter.ui.activities.BaseActivity
import com.adcenter.ui.fragments.SettingsFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ContextModule::class, AppModule::class])
@Singleton
interface AppComponent {

    fun inject(activity: BaseActivity)

    fun inject(fragment: SettingsFragment)
}