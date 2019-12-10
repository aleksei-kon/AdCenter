package com.adcenter.bookmarks.data

import com.adcenter.entities.AdModel

data class BookmarksModel(
    val ads: List<AdModel> = emptyList()
)