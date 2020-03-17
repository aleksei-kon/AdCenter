package com.adcenter.features.bookmarks.usecase

import com.adcenter.features.bookmarks.data.BookmarksModel
import com.adcenter.features.bookmarks.data.BookmarksRequestParams
import com.adcenter.features.bookmarks.repository.IBookmarksRepository
import com.adcenter.utils.Result

class BookmarksUseCase(private val repository: IBookmarksRepository) : IBookmarksUseCase {

    override fun load(requestParams: BookmarksRequestParams): Result<BookmarksModel> =
        Result.Success(loadModel(requestParams))

    private fun loadModel(requestParams: BookmarksRequestParams): BookmarksModel {
        val ads = when (val result = repository.getBookmarks(requestParams)) {
            is Result.Success -> result.value
            is Result.Error -> emptyList()
        }

        return BookmarksModel(ads)
    }
}