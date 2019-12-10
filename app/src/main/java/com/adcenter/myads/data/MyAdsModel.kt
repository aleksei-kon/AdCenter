package com.adcenter.myads.data

import com.adcenter.entities.AdItemModel

data class MyAdsModel(
    val ads: List<AdItemModel> = emptyList()
)