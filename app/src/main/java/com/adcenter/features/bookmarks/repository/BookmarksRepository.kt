package com.adcenter.features.bookmarks.repository

import com.adcenter.datasource.database.AdvertsDao
import com.adcenter.datasource.mappers.AdvertsMapper
import com.adcenter.datasource.network.AdvertsService
import com.adcenter.entities.EmptyPageException
import com.adcenter.entities.Result
import com.adcenter.entities.database.AdItemDbEntity
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.bookmarks.BookmarksConstants.FIRST_PAGE_NUMBER
import com.adcenter.features.bookmarks.models.BookmarksRequestParams

class BookmarksRepository(
    private val advertsService: AdvertsService,
    private val advertsDao: AdvertsDao,
    private val advertsMapper: AdvertsMapper
) : IBookmarksRepository {

    override fun getBookmarks(params: BookmarksRequestParams): Result<List<AdItemModel>> =
        runCatching {
            val networkResponse = advertsService
                .getBookmarks(params.pageNumber, params.isForceRefresh)
                .execute()
                .body()

            if (networkResponse != null && params.isForceRefresh) {
                advertsDao.clear()
            }

            if (networkResponse.isNullOrEmpty() && params.pageNumber != FIRST_PAGE_NUMBER && !params.isForceRefresh) {
                throw EmptyPageException("Empty page ${params.pageNumber}")
            }

            networkResponse
                ?.map { AdItemDbEntity(it) }
                ?.toTypedArray()
                ?.let { advertsDao.insert(*it) }

            val response = advertsDao
                .getAdverts()
                .map { advertsMapper.toAdItem(it) }

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }

    override fun clearDb() {
        advertsDao.clear()
    }
}
