package com.adcenter.di.dagger.module

import com.adcenter.api.Api
import com.adcenter.api.IApi
import com.adcenter.config.IAppConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient()

    @Provides
    fun provideApi(appConfig: IAppConfig): IApi = Api(appConfig)
}