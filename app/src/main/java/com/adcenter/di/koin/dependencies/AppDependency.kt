package com.adcenter.di.koin.dependencies

import com.adcenter.config.AppConfigManager
import com.adcenter.config.BackendUrlHolder
import com.adcenter.config.IAppConfigManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appDependencyModule = module {
    single { BackendUrlHolder(androidContext()) }
    single<IAppConfigManager> { AppConfigManager(androidContext()) }
}