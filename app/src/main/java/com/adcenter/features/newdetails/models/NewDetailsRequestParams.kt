package com.adcenter.features.newdetails.models

import com.adcenter.entities.network.NewDetailsModel

data class NewDetailsRequestParams(
    val newDetailsModel: NewDetailsModel = NewDetailsModel()
)