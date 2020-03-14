package com.adcenter.features.search.usecase

import com.adcenter.features.search.data.SearchModel
import com.adcenter.features.search.data.SearchRequestParams
import com.adcenter.features.search.repository.ISearchRepository
import com.adcenter.utils.Constants.REQUEST_DELAY
import com.adcenter.utils.Result

class SearchUseCase(private val repository: ISearchRepository) : ISearchUseCase {

    override fun load(requestParams: SearchRequestParams): Result<SearchModel> =
        Result.Success(loadModel(requestParams))

    private fun loadModel(requestParams: SearchRequestParams): SearchModel {
        Thread.sleep(REQUEST_DELAY)

        val ads = when (val result = repository.getSearchResult(requestParams)) {
            is Result.Success -> result.value
            is Result.Error -> emptyList()
        }

        return SearchModel(ads)
    }
}