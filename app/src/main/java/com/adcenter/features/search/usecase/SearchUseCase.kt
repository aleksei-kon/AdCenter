package com.adcenter.features.search.usecase

import com.adcenter.features.search.data.SearchModel
import com.adcenter.features.search.data.SearchRequestParams
import com.adcenter.features.search.repository.ISearchRepository
import com.adcenter.utils.Constants.REQUEST_DELAY
import com.adcenter.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class SearchUseCase(private val repository: ISearchRepository) : ISearchUseCase {

    override suspend fun load(requestParams: SearchRequestParams): Result<SearchModel> =
        Result.Success(loadModel(requestParams))

    private suspend fun loadModel(requestParams: SearchRequestParams): SearchModel {
        return coroutineScope {
            val adsAsync = async {
                delay(REQUEST_DELAY)
                repository.getSearchResult(requestParams)
            }
            val adsResult = adsAsync.await()

            val ads = when (adsResult) {
                is Result.Success -> adsResult.value
                is Result.Error -> emptyList()
            }

            SearchModel(ads)
        }
    }
}