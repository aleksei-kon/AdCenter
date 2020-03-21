package com.adcenter.di.koin.dependencies

import com.adcenter.api.Api
import com.adcenter.api.IApi
import com.adcenter.config.*
import com.adcenter.data.processors.AdsDataProcessor
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

//koin module for migration to dagger di
val temporaryModule = module {
    single<IAppConfigManager> {
        AppConfigManager(androidContext())
    }
    single<IBackendUrlHolder> {
        BackendUrlHolder(androidContext())
    }
    single<IAppConfig> {
        AppConfig(
            appConfigManager = get(),
            urlHolder = get()
        )
    }
    factory<IApi> {
        Api(
            appConfig = get()
        )
    }
    factory { Gson() }
    factory {
        AdsDataProcessor(
            gson = get(),
            api = get()
        )
    }
}