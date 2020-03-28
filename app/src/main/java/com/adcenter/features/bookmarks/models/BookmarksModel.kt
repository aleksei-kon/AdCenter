package com.adcenter.features.bookmarks.models

import com.adcenter.entities.view.AdItemModel

data class BookmarksModel(
    val ads: List<AdItemModel> = emptyList()
)