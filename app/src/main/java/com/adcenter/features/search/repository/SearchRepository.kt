package com.adcenter.features.search.repository

import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.api.getSearchUrl
import com.adcenter.data.processors.AdsDataProcessor
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.search.data.SearchRequestParams
import com.adcenter.utils.Result

class SearchRepository(private val processor: AdsDataProcessor) : ISearchRepository {

    override fun getSearchResult(params: SearchRequestParams): Result<List<AdItemModel>> =
        runCatching {
            val request = NetworkDataRequest(getSearchUrl(params))

            val response = Callable<List<AdItemModel>>()
                .setRequest(request)
                .setProcessor(processor)
                .call()

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}
