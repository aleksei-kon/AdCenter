package com.adcenter.koin.dependencies

import com.adcenter.ui.theme.IThemeManager
import com.adcenter.ui.theme.ThemeManager
import com.adcenter.utils.backendurl.BackendUrlHolder
import com.adcenter.utils.resource.IResourceProvider
import com.adcenter.utils.resource.ResourceProvider
import com.adcenter.utils.token.ITokenManager
import com.adcenter.utils.token.TokenManager
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
        single<ITokenManager> { TokenManager(androidContext()) }
    }
}