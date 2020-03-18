package com.adcenter.app

import android.app.Application
import com.adcenter.config.IAppConfig
import com.adcenter.di.dagger.components.AppComponent
import com.adcenter.di.dagger.components.DaggerAppComponent
import com.adcenter.di.dagger.module.ContextModule
import com.adcenter.di.koin.koinModules
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var appConfig: IAppConfig

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

        appComponent.inject(this)
    }

    private fun initRxJava() {
        RxJavaPlugins.setErrorHandler {}
    }

    private fun initAppConfig() {
        appConfig.initConfig()
    }

    companion object {

        lateinit var appComponent: AppComponent
    }
}