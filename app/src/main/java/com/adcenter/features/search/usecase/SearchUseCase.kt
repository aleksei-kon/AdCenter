package com.adcenter.features.search.usecase

import com.adcenter.entities.Result
import com.adcenter.features.search.models.SearchModel
import com.adcenter.features.search.models.SearchRequestParams
import com.adcenter.features.search.repository.ISearchRepository

class SearchUseCase(private val repository: ISearchRepository) : ISearchUseCase {

    override fun load(requestParams: SearchRequestParams): Result<SearchModel> =
        when (val result = repository.getSearchResult(requestParams)) {
            is Result.Success -> Result.Success(SearchModel(result.value))
            is Result.Error -> Result.Error(result.exception)
        }

    override fun clearDb() {
        repository.clearDb()
    }
}