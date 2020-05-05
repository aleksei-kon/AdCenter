package com.adcenter.features.bookmarks.usecase

import com.adcenter.features.bookmarks.models.BookmarksModel
import com.adcenter.features.bookmarks.models.BookmarksRequestParams
import com.adcenter.entities.Result

interface IBookmarksUseCase {

    fun load(requestParams: BookmarksRequestParams): Result<BookmarksModel>

    fun clearDb()
}