package com.adcenter.bookmarks.usecase

import com.adcenter.bookmarks.data.BookmarksModel
import com.adcenter.bookmarks.data.BookmarksRequestParams
import com.adcenter.bookmarks.repository.IBookmarksRepository
import com.adcenter.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class BookmarksUseCase(private val repository: IBookmarksRepository) : IBookmarksUseCase {

    override suspend fun load(requestParams: BookmarksRequestParams): Result<BookmarksModel> =
        Result.Success(loadModel(requestParams))

    private suspend fun loadModel(requestParams: BookmarksRequestParams): BookmarksModel {
        return coroutineScope {
            val adsAsync = async { repository.getBookmarks(requestParams) }
            val adsResult = adsAsync.await()

            val ads = when (adsResult) {
                is Result.Success -> adsResult.value
                is Result.Error -> emptyList()
            }

            BookmarksModel(ads)
        }
    }
}