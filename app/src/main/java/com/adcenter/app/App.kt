package com.adcenter.app

import android.app.Application
import com.adcenter.config.AppConfig
import com.adcenter.di.dagger.components.AppComponent
import com.adcenter.di.dagger.components.DaggerAppComponent
import com.adcenter.di.dagger.module.ContextModule
import com.adcenter.di.koin.koinModules
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initDagger()
        initRxJava()
        initAppConfig()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(koinModules)
        }
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }

    private fun initRxJava() {
        RxJavaPlugins.setErrorHandler {}
    }

    private fun initAppConfig() {
        AppConfig.initConfig()
    }

    companion object {

        lateinit var appComponent: AppComponent
    }
}