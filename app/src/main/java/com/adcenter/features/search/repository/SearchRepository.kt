package com.adcenter.features.search.repository

import com.adcenter.datasource.database.AdvertsDao
import com.adcenter.datasource.mappers.AdvertsMapper
import com.adcenter.datasource.network.AdvertsService
import com.adcenter.entities.Result
import com.adcenter.entities.database.AdItemDbEntity
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.search.models.SearchRequestParams

class SearchRepository(
    private val advertsService: AdvertsService,
    private val advertsDao: AdvertsDao,
    private val advertsMapper: AdvertsMapper
) : ISearchRepository {

    override fun getSearchResult(params: SearchRequestParams): Result<List<AdItemModel>> =
        runCatching {
            val networkResponse = advertsService
                .getSearch(
                    searchText = params.searchText,
                    pageNumber = params.pageNumber,
                    sortType = params.sortType
                )
                .execute()
                .body()

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
}
