package com.adcenter.entities.network

import com.adcenter.extensions.Constants

data class NewDetailsModel(
    val photos: List<String> = emptyList(),
    val title: String = Constants.EMPTY,
    val type: String = Constants.EMPTY,
    val price: String = Constants.EMPTY,
    val location: String = Constants.EMPTY,
    val synopsis: String = Constants.EMPTY,
    val phone: String = Constants.EMPTY
)