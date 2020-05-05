package com.adcenter.entities.view

import com.adcenter.extensions.Constants.EMPTY

data class AdItemModel(
    val id: Int = -1,
    val photoUrl: String = EMPTY,
    val title: String = EMPTY,
    val price: String = EMPTY,
    val place: String = EMPTY,
    val date: String = EMPTY,
    val views: String = EMPTY
)