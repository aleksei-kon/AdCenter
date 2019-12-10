package com.adcenter.bookmarks.repository

import com.adcenter.bookmarks.data.BookmarksRequestParams
import com.adcenter.entities.AdModel
import com.adcenter.utils.Result

interface IBookmarksRepository {

    suspend fun getBookmarks(params: BookmarksRequestParams): Result<List<AdModel>>
}