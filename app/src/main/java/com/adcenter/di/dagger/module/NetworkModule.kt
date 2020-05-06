package com.adcenter.di.dagger.module

import com.adcenter.appconfig.IAppConfig
import com.adcenter.datasource.network.AccountService
import com.adcenter.datasource.network.AdvertsService
import com.adcenter.datasource.network.ImageService
import com.adcenter.extensions.Constants.Request.AUTHORIZATION_HEADER
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(appConfig: IAppConfig): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()

                appConfig.token?.let { token ->
                    requestBuilder.addHeader(AUTHORIZATION_HEADER, token)
                }

                chain.proceed(requestBuilder.build())
            }
            .build()

    @Provides
    fun provideRetrofit(httpClient: OkHttpClient, appConfig: IAppConfig): Retrofit =
        Retrofit.Builder()
            .baseUrl(appConfig.backendUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

    @Provides
    fun provideAccountService(retrofit: Retrofit): AccountService =
        retrofit.create(AccountService::class.java)

    @Provides
    fun provideAdvertService(retrofit: Retrofit): AdvertsService =
        retrofit.create(AdvertsService::class.java)

    @Provides
    fun provideImageService(retrofit: Retrofit): ImageService =
        retrofit.create(ImageService::class.java)
}