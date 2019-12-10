package com.adcenter.features.bookmarks.repository

import com.adcenter.features.bookmarks.data.BookmarksRequestParams
import com.adcenter.entities.AdItemModel
import com.adcenter.utils.Result

interface IBookmarksRepository {

    suspend fun getBookmarks(params: BookmarksRequestParams): Result<List<AdItemModel>>
}