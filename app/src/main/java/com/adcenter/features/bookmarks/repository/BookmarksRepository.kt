package com.adcenter.features.bookmarks.repository

import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.getBookmarksUrl
import com.adcenter.data.processors.AdsDataProcessor
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.bookmarks.data.BookmarksRequestParams
import com.adcenter.utils.Result

class BookmarksRepository(private val processor: AdsDataProcessor) : IBookmarksRepository {

    override fun getBookmarks(params: BookmarksRequestParams): Result<List<AdItemModel>> =
        runCatching {
            val request = NetworkDataRequest(getBookmarksUrl(params))

            val response = Callable<List<AdItemModel>>()
                .setRequest(request)
                .setProcessor(processor)
                .call()

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}
