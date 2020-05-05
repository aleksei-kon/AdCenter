package com.adcenter.di.dagger.module

import android.content.Context
import com.adcenter.appconfig.*
import com.adcenter.resource.IResourceProvider
import com.adcenter.resource.ResourceProvider
import com.adcenter.ui.theme.IThemeManager
import com.adcenter.ui.theme.ThemeManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideResourceProvider(context: Context): IResourceProvider = ResourceProvider(context)

    @Provides
    @Singleton
    fun provideThemeManager(context: Context): IThemeManager = ThemeManager(context)

    @Provides
    @Singleton
    fun provideAppConfigManager(context: Context): IAppConfigManager = AppConfigManager(context)

    @Provides
    @Singleton
    fun provideBackendUrlHolder(context: Context): IBackendUrlHolder = BackendUrlHolder(context)

    @Provides
    @Singleton
    fun provideAppConfig(
        urlHolder: IBackendUrlHolder,
        appConfigManager: IAppConfigManager,
        context: Context
    ): IAppConfig = AppConfig(urlHolder, appConfigManager, context)
}