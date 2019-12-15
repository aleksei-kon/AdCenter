package com.adcenter.entities.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NetworkAdItem(
    val id: String,
    val photoUrl: String?,
    val title: String?,
    val price: String?,
    val place: String?,
    val date: Long?,
    val views: Int?
) : Parcelable