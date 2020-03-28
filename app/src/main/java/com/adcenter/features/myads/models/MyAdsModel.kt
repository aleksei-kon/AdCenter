package com.adcenter.features.myads.models

import com.adcenter.entities.view.AdItemModel

data class MyAdsModel(
    val ads: List<AdItemModel> = emptyList()
)