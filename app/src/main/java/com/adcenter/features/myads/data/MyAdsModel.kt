package com.adcenter.features.myads.data

import com.adcenter.entities.AdItemModel

data class MyAdsModel(
    val ads: List<AdItemModel> = emptyList()
)