package com.adcenter.bookmarks.data

import com.adcenter.entities.AdItemModel

data class BookmarksModel(
    val ads: List<AdItemModel> = emptyList()
)