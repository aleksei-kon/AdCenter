package com.adcenter.features.search.repository

import com.adcenter.api.IApi
import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.processors.AdsDataProcessor
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.search.data.SearchRequestParams
import com.adcenter.utils.Result
import javax.inject.Inject

class SearchRepository(private val processor: AdsDataProcessor) : ISearchRepository {

    @Inject
    lateinit var api: IApi

    init {
        Injector.appComponent.inject(this)
    }

    override fun getSearchResult(params: SearchRequestParams): Result<List<AdItemModel>> =
        runCatching {
            val request = NetworkDataRequest(api.getSearchUrl(params))

            val response = Callable<List<AdItemModel>>()
                .setRequest(request)
                .setProcessor(processor)
                .call()

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}
