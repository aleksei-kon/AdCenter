package com.adcenter.bookmarks.repository

import com.adcenter.bookmarks.data.BookmarksRequestParams
import com.adcenter.entities.AdItemModel
import com.adcenter.utils.Result

interface IBookmarksRepository {

    suspend fun getBookmarks(params: BookmarksRequestParams): Result<List<AdItemModel>>
}