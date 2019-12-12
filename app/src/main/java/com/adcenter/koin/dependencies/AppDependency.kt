package com.adcenter.koin.dependencies

import com.adcenter.app.theme.IThemeManager
import com.adcenter.app.theme.ThemeManager
import com.adcenter.utils.IResourceProvider
import com.adcenter.utils.ResourceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object AppDependency {
    val module = module {
        single<IResourceProvider> { ResourceProvider(androidContext()) }
        single<IThemeManager> { ThemeManager(androidContext()) }
    }
}