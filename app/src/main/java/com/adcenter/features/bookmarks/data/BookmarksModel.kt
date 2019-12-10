package com.adcenter.features.bookmarks.data

import com.adcenter.entities.AdItemModel

data class BookmarksModel(
    val ads: List<AdItemModel> = emptyList()
)