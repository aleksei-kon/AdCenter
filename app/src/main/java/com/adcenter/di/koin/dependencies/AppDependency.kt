package com.adcenter.di.koin.dependencies

import com.adcenter.config.AppConfigManager
import com.adcenter.config.BackendUrlHolder
import com.adcenter.config.IAppConfigManager
import com.adcenter.resource.IResourceProvider
import com.adcenter.resource.ResourceProvider
import com.adcenter.theme.IThemeManager
import com.adcenter.theme.ThemeManager
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appDependencyModule = module {
    single { Gson() }
    single { BackendUrlHolder(androidContext()) }
    single<IThemeManager> { ThemeManager(androidContext()) }
    single<IAppConfigManager> { AppConfigManager(androidContext()) }
}