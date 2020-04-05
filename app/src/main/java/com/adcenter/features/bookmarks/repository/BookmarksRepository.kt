package com.adcenter.features.bookmarks.repository

import com.adcenter.entities.Result
import com.adcenter.datasource.mappers.AdsMapper
import com.adcenter.datasource.network.AdvertService
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.bookmarks.models.BookmarksRequestParams

class BookmarksRepository(
    private val advertService: AdvertService,
    private val adsMapper: AdsMapper
) : IBookmarksRepository {

    override fun getBookmarks(params: BookmarksRequestParams): Result<List<AdItemModel>> =
        runCatching {
            val networkResponse = advertService
                .getBookmarks(params.pageNumber)
                .execute()
                .body()

            val response = adsMapper.map(networkResponse)

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}
