package com.adcenter.features.bookmarks.usecase

import com.adcenter.entities.Result
import com.adcenter.features.bookmarks.models.BookmarksModel
import com.adcenter.features.bookmarks.models.BookmarksRequestParams
import com.adcenter.features.bookmarks.repository.IBookmarksRepository

class BookmarksUseCase(private val repository: IBookmarksRepository) : IBookmarksUseCase {

    override fun load(requestParams: BookmarksRequestParams): Result<BookmarksModel> =
        when (val result = repository.getBookmarks(requestParams)) {
            is Result.Success -> Result.Success(BookmarksModel(result.value))
            is Result.Error -> Result.Error(result.exception)
        }

    override fun clearDb() {
        repository.clearDb()
    }
}