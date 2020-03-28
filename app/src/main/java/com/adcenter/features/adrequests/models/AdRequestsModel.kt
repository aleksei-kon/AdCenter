package com.adcenter.features.adrequests.models

import com.adcenter.entities.view.AdItemModel

data class AdRequestsModel(
    val ads: List<AdItemModel> = emptyList()
)