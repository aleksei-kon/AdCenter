package com.adcenter.features.myads.data

import com.adcenter.entities.view.AdItemModel

data class MyAdsModel(
    val ads: List<AdItemModel> = emptyList()
)