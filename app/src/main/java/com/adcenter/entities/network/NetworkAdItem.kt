package com.adcenter.entities.network

data class NetworkAdItem(
    val id: Int,
    val photoUrl: String?,
    val title: String?,
    val price: String?,
    val place: String?,
    val date: Long?,
    val views: Int?
)