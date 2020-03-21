package com.adcenter.app

import android.app.Application
import com.adcenter.config.IAppConfig
import com.adcenter.di.dagger.injector.Injector
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var appConfig: IAppConfig

    override fun onCreate() {
        super.onCreate()

        initDagger()
        initRxJava()
        initAppConfig()
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
}