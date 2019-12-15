package com.adcenter.entities.network

import com.adcenter.utils.Constants.EMPTY

data class NetworkDetailsModel(
    val id: String = EMPTY,
    val photos: List<String>? = null,
    val isBookmark: Boolean? = null,
    val title: String? = null,
    val price: String? = null,
    val location: String? = null,
    val date: Long? = null,
    val views: Int? = null,
    val synopsis: String? = null,
    val username: String? = null,
    val phone: String? = null
)
