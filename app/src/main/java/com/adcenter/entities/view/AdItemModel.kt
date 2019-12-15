package com.adcenter.entities.view

import com.adcenter.utils.Constants.EMPTY

data class AdItemModel(
    val id: String = EMPTY,
    val photoUrl: String = EMPTY,
    val title: String = EMPTY,
    val price: String = EMPTY,
    val place: String = EMPTY,
    val date: String = EMPTY,
    val views: String = EMPTY
)