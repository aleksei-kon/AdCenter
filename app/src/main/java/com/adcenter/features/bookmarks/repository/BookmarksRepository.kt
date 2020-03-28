package com.adcenter.features.bookmarks.repository

import com.adcenter.datasource.api.IApi
import com.adcenter.datasource.Callable
import com.adcenter.datasource.NetworkDataRequest
import com.adcenter.datasource.processors.AdsDataProcessor
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.bookmarks.models.BookmarksRequestParams
import com.adcenter.datasource.Result

class BookmarksRepository(
    private val processor: AdsDataProcessor,
    private val api: IApi
) : IBookmarksRepository {

    override fun getBookmarks(params: BookmarksRequestParams): Result<List<AdItemModel>> =
        runCatching {
            val request = NetworkDataRequest(
                api.getBookmarksUrl(
                    params
                )
            )

            val response = Callable<List<AdItemModel>>()
                .setRequest(request)
                .setProcessor(processor)
                .call()

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}
