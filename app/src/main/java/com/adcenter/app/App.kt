package com.adcenter.app

import android.app.Application
import com.adcenter.di.dagger.injector.Injector
import io.reactivex.plugins.RxJavaPlugins

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initDagger()
        initRxJava()
    }

    private fun initDagger() {
        Injector.init(this)
    }

    private fun initRxJava() {
        RxJavaPlugins.setErrorHandler {}
    }
}