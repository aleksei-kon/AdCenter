package com.adcenter.bookmarks.repository

import com.adcenter.bookmarks.data.BookmarksRequestParams
import com.adcenter.entities.AdModel
import com.adcenter.utils.Result
import kotlinx.coroutines.*

class BookmarksRepository : IBookmarksRepository {

    override suspend fun getBookmarks(params: BookmarksRequestParams): Result<List<AdModel>> {
        return withContext(Dispatchers.IO) {

            delay(4000)

            suspendCancellableCoroutine<Result<List<AdModel>>> { continuation ->
                runCatching {
                    val response = testResult(params)

                    if (isActive) {
                        continuation.resume(
                            Result.Success(response)
                        ) {}
                    } else {
                        continuation.cancel()
                    }
                }.onFailure {
                    continuation.resume(Result.Error(it)) {}
                }
            }
        }
    }

    private fun testResult(params: BookmarksRequestParams): List<AdModel> {
        val response = mutableListOf<AdModel>()

        for (number in params.pageNumber * 10 until (params.pageNumber + 1) * 10) {
            response.add(AdModel("$number bookmarks"))
        }

        return response
    }
}