package com.adcenter.features.bookmarks.usecase

import com.adcenter.features.bookmarks.data.BookmarksModel
import com.adcenter.features.bookmarks.data.BookmarksRequestParams
import com.adcenter.utils.Result

interface IBookmarksUseCase {

    fun load(requestParams: BookmarksRequestParams): Result<BookmarksModel>
}