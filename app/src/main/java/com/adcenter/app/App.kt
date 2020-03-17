package com.adcenter.app

import android.app.Application
import com.adcenter.app.config.AppConfig
import com.adcenter.dagger.components.AppComponent
import com.adcenter.dagger.components.DaggerAppComponent
import com.adcenter.dagger.module.ContextModule
import com.adcenter.koin.initKoin
import io.reactivex.plugins.RxJavaPlugins

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler {}
        initKoin()
        initDagger()
        AppConfig.initConfig()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }

    companion object {

        lateinit var appComponent: AppComponent
            private set
    }
}