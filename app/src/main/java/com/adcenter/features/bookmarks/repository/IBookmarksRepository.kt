package com.adcenter.features.bookmarks.repository

import com.adcenter.features.bookmarks.models.BookmarksRequestParams
import com.adcenter.entities.view.AdItemModel
import com.adcenter.datasource.Result

interface IBookmarksRepository {

    fun getBookmarks(params: BookmarksRequestParams): Result<List<AdItemModel>>
}