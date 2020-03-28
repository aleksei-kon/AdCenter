package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.annotations.ActivityScope
import com.adcenter.di.dagger.module.RegistrationModule
import com.adcenter.ui.activities.RegisterActivity
import dagger.Subcomponent

@Subcomponent(modules = [RegistrationModule::class])
@ActivityScope
interface RegistrationComponent {

    fun inject(activity: RegisterActivity)
}