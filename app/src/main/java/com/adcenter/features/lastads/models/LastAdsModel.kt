package com.adcenter.features.lastads.models

import com.adcenter.entities.view.AdItemModel

data class LastAdsModel(
    val ads: List<AdItemModel> = emptyList()
)
