package com.adcenter.features.search.repository

import com.adcenter.datasource.api.IApi
import com.adcenter.datasource.Callable
import com.adcenter.datasource.NetworkDataRequest
import com.adcenter.datasource.processors.AdsDataProcessor
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.search.models.SearchRequestParams
import com.adcenter.datasource.Result

class SearchRepository(
    private val processor: AdsDataProcessor,
    private val api: IApi
) : ISearchRepository {

    override fun getSearchResult(params: SearchRequestParams): Result<List<AdItemModel>> =
        runCatching {
            val request = NetworkDataRequest(
                api.getSearchUrl(params)
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
