package com.adcenter.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adcenter.datasource.database.converters.Converters
import com.adcenter.entities.database.AdItemDbEntity
import com.adcenter.entities.database.DetailsDbEntity

const val APP_DB_NAME = "ad_center_data_base"

@Database(
    entities = [
        AdItemDbEntity::class,
        DetailsDbEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAdvertsDao(): AdvertsDao

    abstract fun getDetailsDao(): DetailsDao
}