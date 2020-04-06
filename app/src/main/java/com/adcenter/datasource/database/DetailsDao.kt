package com.adcenter.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adcenter.entities.database.DetailsDbEntity

@Dao
abstract class DetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg entity: DetailsDbEntity)

    @Query("SELECT * FROM details WHERE id = :id LIMIT 1")
    abstract fun getDetails(id: String): DetailsDbEntity

    @Query("DELETE FROM details")
    abstract fun clear()
}