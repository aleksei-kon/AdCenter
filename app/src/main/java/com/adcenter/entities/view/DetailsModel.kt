package com.adcenter.entities.view

import com.adcenter.utils.Constants.EMPTY

data class DetailsModel(
    val id: String = EMPTY,
    val photos: List<String> = emptyList(),
    val isBookmark: Boolean = false,
    val isShown: Boolean = false,
    val title: String = EMPTY,
    val price: String = EMPTY,
    val location: String = EMPTY,
    val date: String = EMPTY,
    val views: String = EMPTY,
    val synopsis: String = EMPTY,
    val username: String = EMPTY,
    val phone: String = EMPTY
)