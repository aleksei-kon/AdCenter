package com.adcenter.di.dagger.module

import android.content.Context
import com.adcenter.config.AppConfig
import com.adcenter.config.AppConfigManager
import com.adcenter.config.BackendUrlHolder
import com.adcenter.config.IAppConfigManager
import com.adcenter.resource.IResourceProvider
import com.adcenter.resource.ResourceProvider
import com.adcenter.theme.IThemeManager
import com.adcenter.theme.ThemeManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class AppModule {

    @Provides
    fun provideGson(): Gson = Gson()

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
    fun provideBackendUrlHolder(context: Context): BackendUrlHolder = BackendUrlHolder(context)

    @Provides
    @Singleton
    fun provideAppConfig(
        urlHolder: BackendUrlHolder,
        appConfigManager: IAppConfigManager
    ): AppConfig = AppConfig(urlHolder, appConfigManager)
}