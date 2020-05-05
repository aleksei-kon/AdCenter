package com.adcenter.features.bookmarks.usecase

import com.adcenter.features.bookmarks.models.BookmarksModel
import com.adcenter.features.bookmarks.models.BookmarksRequestParams
import com.adcenter.features.bookmarks.repository.IBookmarksRepository
import com.adcenter.entities.Result

class BookmarksUseCase(private val repository: IBookmarksRepository) : IBookmarksUseCase {

    override fun load(requestParams: BookmarksRequestParams): Result<BookmarksModel> =
        Result.Success(loadModel(requestParams))

    override fun clearDb() {
        repository.clearDb()
    }

    private fun loadModel(requestParams: BookmarksRequestParams): BookmarksModel {
        val ads = when (val result = repository.getBookmarks(requestParams)) {
            is Result.Success -> result.value
            is Result.Error -> emptyList()
        }

        return BookmarksModel(ads)
    }
}