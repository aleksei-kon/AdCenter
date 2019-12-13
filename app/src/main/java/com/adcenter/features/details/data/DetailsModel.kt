package com.adcenter.features.details.data

import com.adcenter.utils.Constants.EMPTY

data class DetailsModel(
    val photos: List<String> = emptyList(),
    val isBookmark: Boolean = false,
    val title: String = EMPTY,
    val price: String = EMPTY,
    val location: String = EMPTY,
    val date: String = EMPTY,
    val views: Int = 0,
    val synopsis: String = EMPTY,
    val username: String = EMPTY,
    val phone: String = EMPTY
)