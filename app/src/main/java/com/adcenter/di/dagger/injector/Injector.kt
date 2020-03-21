package com.adcenter.di.dagger.injector

import android.content.Context
import com.adcenter.di.dagger.components.AppComponent
import com.adcenter.di.dagger.components.DaggerAppComponent
import com.adcenter.di.dagger.components.LastAdsComponent
import com.adcenter.di.dagger.module.ContextModule
import com.adcenter.di.dagger.module.LastAdsModule

object Injector {

    private var appComponentInstance: AppComponent? = null

    private var lastAdsComponent: LastAdsComponent? = null

    val appComponent: AppComponent
        get() = appComponentInstance ?: throw IllegalStateException("AppComponent is null")

    fun init(context: Context) {
        if (appComponentInstance == null) {
            appComponentInstance = DaggerAppComponent.builder()
                .contextModule(ContextModule(context))
                .build()
        }
    }

    fun plusLastAdsComponent(): LastAdsComponent {
        if (lastAdsComponent == null) {
            lastAdsComponent = appComponent.plusLastAdsComponent(LastAdsModule())
        }

        return lastAdsComponent ?: throw IllegalStateException("LastAdsComponent is null")
    }

    fun clearLastAdsComponent() {
        lastAdsComponent = null
    }
}