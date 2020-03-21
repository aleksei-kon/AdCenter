package com.adcenter.app

import android.app.Application
import com.adcenter.config.IAppConfig
import com.adcenter.di.dagger.injector.Injector
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
        Injector.init(this)
        Injector.appComponent.inject(this)
    }

    private fun initRxJava() {
        RxJavaPlugins.setErrorHandler {}
    }

    private fun initAppConfig() {
        appConfig.initConfig()
    }

    companion object {

    }
}