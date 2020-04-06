package com.adcenter.di.dagger.module

import com.adcenter.appconfig.IAppConfig
import com.adcenter.datasource.mappers.AdvertsMapper
import com.adcenter.datasource.mappers.AppConfigMapper
import com.adcenter.datasource.mappers.DetailsMapper
import com.adcenter.resource.IResourceProvider
import dagger.Module
import dagger.Provides

@Module
class MappersModule {

    @Provides
    fun provideAppConfigMapper(): AppConfigMapper = AppConfigMapper()

    @Provides
    fun provideAdsMapper(appConfig: IAppConfig): AdvertsMapper = AdvertsMapper(appConfig)

    @Provides
    fun provideDetailsMapper(
        appConfig: IAppConfig,
        resourceProvider: IResourceProvider
    ): DetailsMapper = DetailsMapper(appConfig, resourceProvider)
}