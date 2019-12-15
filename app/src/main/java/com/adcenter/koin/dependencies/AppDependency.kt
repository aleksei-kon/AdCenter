package com.adcenter.koin.dependencies

import com.adcenter.ui.theme.IThemeManager
import com.adcenter.ui.theme.ThemeManager
import com.adcenter.app.config.backendurl.BackendUrlHolder
import com.adcenter.utils.resource.IResourceProvider
import com.adcenter.utils.resource.ResourceProvider
import com.adcenter.app.config.IAppConfigManager
import com.adcenter.app.config.AppConfigManager
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object AppDependency {
    val module = module {
        single { Gson() }
        single { OkHttpClient() }
        single { BackendUrlHolder(androidContext()) }
        single<IResourceProvider> { ResourceProvider(androidContext()) }
        single<IThemeManager> { ThemeManager(androidContext()) }
        single<IAppConfigManager> { AppConfigManager(androidContext()) }
    }
}