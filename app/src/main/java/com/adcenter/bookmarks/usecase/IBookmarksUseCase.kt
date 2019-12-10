package com.adcenter.bookmarks.usecase

import com.adcenter.bookmarks.data.BookmarksModel
import com.adcenter.bookmarks.data.BookmarksRequestParams
import com.adcenter.utils.Result

interface IBookmarksUseCase {

    suspend fun load(requestParams: BookmarksRequestParams): Result<BookmarksModel>
}