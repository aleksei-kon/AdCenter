package com.adcenter.entities.network

data class NetworkDetailsModel(
    val id: Int = -1,
    val photos: List<String>? = null,
    val bookmark: Boolean? = null,
    val showed: Boolean? = null,
    val title: String? = null,
    val price: String? = null,
    val location: String? = null,
    val date: Long? = null,
    val views: Int? = null,
    val synopsis: String? = null,
    val username: String? = null,
    val phone: String? = null
)
