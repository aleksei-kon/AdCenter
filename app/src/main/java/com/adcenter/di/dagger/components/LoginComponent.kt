package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.annotations.ActivityScope
import com.adcenter.di.dagger.module.LoginModule
import com.adcenter.ui.activities.LoginActivity
import dagger.Subcomponent

@Subcomponent(modules = [LoginModule::class])
@ActivityScope
interface LoginComponent {

    fun inject(activity: LoginActivity)
}