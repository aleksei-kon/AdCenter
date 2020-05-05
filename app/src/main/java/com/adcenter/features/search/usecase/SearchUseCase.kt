package com.adcenter.features.search.usecase

import com.adcenter.features.search.models.SearchModel
import com.adcenter.features.search.models.SearchRequestParams
import com.adcenter.features.search.repository.ISearchRepository
import com.adcenter.entities.Result

class SearchUseCase(private val repository: ISearchRepository) : ISearchUseCase {

    override fun load(requestParams: SearchRequestParams): Result<SearchModel> =
        Result.Success(loadModel(requestParams))

    override fun clearDb() {
        repository.clearDb()
    }

    private fun loadModel(requestParams: SearchRequestParams): SearchModel {
        val ads = when (val result = repository.getSearchResult(requestParams)) {
            is Result.Success -> result.value
            is Result.Error -> emptyList()
        }

        return SearchModel(ads)
    }
}