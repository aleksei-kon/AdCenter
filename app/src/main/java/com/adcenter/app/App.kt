package com.adcenter.app

import android.app.Application
import com.adcenter.koin.initKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()

        AppConfig.initConfig()
    }
}