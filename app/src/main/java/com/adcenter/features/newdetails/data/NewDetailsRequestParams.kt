package com.adcenter.features.newdetails.data

import com.adcenter.utils.Constants

data class NewDetailsRequestParams(
    val photos: List<String> = emptyList(),
    val title: String = Constants.EMPTY,
    val price: String = Constants.EMPTY,
    val location: String = Constants.EMPTY,
    val synopsis: String = Constants.EMPTY,
    val phone: String = Constants.EMPTY
)