package com.adcenter.di.dagger.module

import android.content.Context
import androidx.room.Room
import com.adcenter.datasource.database.APP_DB_NAME
import com.adcenter.datasource.database.AdvertsDao
import com.adcenter.datasource.database.AppDatabase
import com.adcenter.datasource.database.DetailsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase = Room
        .databaseBuilder(
            context,
            AppDatabase::class.java,
            APP_DB_NAME
        ).build()

    @Provides
    @Singleton
    fun provideAdvertDao(appDatabase: AppDatabase): AdvertsDao = appDatabase.getAdvertsDao()

    @Provides
    @Singleton
    fun provideDetailsDao(appDatabase: AppDatabase): DetailsDao = appDatabase.getDetailsDao()
}