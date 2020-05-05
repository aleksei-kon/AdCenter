package com.adcenter.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adcenter.entities.database.AdItemDbEntity

@Dao
abstract class AdvertsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg entity: AdItemDbEntity)

    @Query("SELECT * FROM ad_items ORDER BY date DESC, id")
    abstract fun getAdverts(): List<AdItemDbEntity>

    @Query("DELETE FROM ad_items")
    abstract fun clear()
}