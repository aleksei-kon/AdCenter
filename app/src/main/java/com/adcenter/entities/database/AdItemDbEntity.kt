package com.adcenter.entities.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.adcenter.entities.network.NetworkAdItem

@Entity(tableName = "ad_items")
data class AdItemDbEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "photo_url")
    val photoUrl: String?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "price")
    val price: String?,

    @ColumnInfo(name = "place")
    val place: String?,

    @ColumnInfo(name = "date")
    val date: Long?,

    @ColumnInfo(name = "views")
    val views: Int?
) {

    @Ignore
    constructor(adItem: NetworkAdItem) : this(
        id = adItem.id,
        photoUrl = adItem.photoUrl,
        title = adItem.title,
        price = adItem.price,
        place = adItem.place,
        date = adItem.date,
        views = adItem.views
    )
}