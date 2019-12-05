package com.adcenter.koin

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

fun Application.initKoin() {
    startKoin {
        androidContext(this@initKoin)
        modules(koinModules)
    }
}