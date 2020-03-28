package com.adcenter.features.search.models

import com.adcenter.entities.view.AdItemModel

data class SearchModel(
    val ads: List<AdItemModel> = emptyList()
)