package com.adcenter.entities.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.adcenter.entities.network.NetworkDetailsModel
import com.adcenter.extensions.Constants.EMPTY_ID

@Entity(tableName = "details")
data class DetailsDbEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = EMPTY_ID,

    @ColumnInfo(name = "photos")
    val photos: List<String>? = null,

    @ColumnInfo(name = "is_bookmark")
    val isBookmark: Boolean? = null,

    @ColumnInfo(name = "is_shown")
    val isShown: Boolean? = null,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "price")
    val price: String? = null,

    @ColumnInfo(name = "location")
    val location: String? = null,

    @ColumnInfo(name = "date")
    val date: Long? = null,

    @ColumnInfo(name = "views")
    val views: Int? = null,

    @ColumnInfo(name = "synopsis")
    val synopsis: String? = null,

    @ColumnInfo(name = "username")
    val username: String? = null,

    @ColumnInfo(name = "phone")
    val phone: String? = null
) {

    @Ignore
    constructor(details: NetworkDetailsModel) : this(
        id = details.id,
        photos = details.photos,
        isBookmark = details.isBookmark,
        isShown = details.isShown,
        title = details.title,
        price = details.price,
        location = details.location,
        date = details.date,
        views = details.views,
        synopsis = details.synopsis,
        username = details.username,
        phone = details.phone
    )
}
