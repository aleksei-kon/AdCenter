package com.adcenter.app

import android.app.Application
import com.adcenter.app.config.AppConfig
import com.adcenter.koin.initKoin
import io.reactivex.plugins.RxJavaPlugins

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler {}
        initKoin()
        AppConfig.initConfig()
    }
}