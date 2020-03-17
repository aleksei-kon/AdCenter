package com.adcenter.app

import android.app.Application
import com.adcenter.config.AppConfig
import com.adcenter.koin.koinModules
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initRxJava()
        initAppConfig()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(koinModules)
        }
    }

    private fun initRxJava() {
        RxJavaPlugins.setErrorHandler {}
    }

    private fun initAppConfig() {
        AppConfig.initConfig()
    }
}