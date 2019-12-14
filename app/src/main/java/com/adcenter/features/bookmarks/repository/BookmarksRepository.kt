package com.adcenter.features.bookmarks.repository

import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.getBookmarksUrl
import com.adcenter.data.processors.AdsDataProcessor
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.bookmarks.data.BookmarksRequestParams
import com.adcenter.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

class BookmarksRepository : IBookmarksRepository {

    private val processor = AdsDataProcessor()

    override suspend fun getBookmarks(params: BookmarksRequestParams): Result<List<AdItemModel>> {
        return withContext(Dispatchers.IO) {
            suspendCancellableCoroutine<Result<List<AdItemModel>>> { continuation ->
                runCatching {
                    val request = NetworkDataRequest(getBookmarksUrl(params))

                    val response = Callable<List<AdItemModel>>()
                        .setRequest(request)
                        .setProcessor(processor)
                        .call()

                    if (isActive) {
                        continuation.resume(Result.Success(response)) {}
                    } else {
                        continuation.cancel()
                    }
                }.onFailure {
                    continuation.resume(Result.Error(it)) {}
                }
            }
        }
    }
}