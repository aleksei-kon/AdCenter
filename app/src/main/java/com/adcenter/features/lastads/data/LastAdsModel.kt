package com.adcenter.features.lastads.data

import com.adcenter.entities.view.AdItemModel

data class LastAdsModel(
    val ads: List<AdItemModel> = emptyList()
)
