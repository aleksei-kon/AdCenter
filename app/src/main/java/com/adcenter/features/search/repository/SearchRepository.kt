package com.adcenter.features.search.repository

import com.adcenter.entities.Result
import com.adcenter.datasource.mappers.AdsMapper
import com.adcenter.datasource.network.AdvertService
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.search.models.SearchRequestParams

class SearchRepository(
    private val advertService: AdvertService,
    private val adsMapper: AdsMapper
) : ISearchRepository {

    override fun getSearchResult(params: SearchRequestParams): Result<List<AdItemModel>> =
        runCatching {
            val networkResponse = advertService
                .getSearch(
                    searchText = params.searchText,
                    pageNumber = params.pageNumber,
                    sortType = params.sortType
                )
                .execute()
                .body()

            val response = adsMapper.map(networkResponse)

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}
