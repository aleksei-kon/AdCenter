package com.adcenter.di.dagger.injector

import android.content.Context
import com.adcenter.di.dagger.components.AppComponent
import com.adcenter.di.dagger.components.DaggerAppComponent

object Injector {

    private var appComponentInstance: AppComponent? = null

    val appComponent: AppComponent
        get() = appComponentInstance ?: throw IllegalStateException("AppComponent is null")

    fun init(context: Context) {
        if (appComponentInstance == null) {
            appComponentInstance = DaggerAppComponent.builder()
                .context(context)
                .build()
        }
    }
}