package com.adcenter.di.dagger.module

import android.content.Context
import com.adcenter.resource.IResourceProvider
import com.adcenter.resource.ResourceProvider
import com.adcenter.theme.IThemeManager
import com.adcenter.theme.ThemeManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideThemeManager(context: Context): IThemeManager = ThemeManager(context)

    @Provides
    @Singleton
    fun provideResourceManager(context: Context): IResourceProvider = ResourceProvider(context)
}