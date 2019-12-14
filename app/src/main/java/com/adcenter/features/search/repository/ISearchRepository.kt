package com.adcenter.features.search.repository

import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.search.data.SearchRequestParams
import com.adcenter.utils.Result

interface ISearchRepository {

    suspend fun getSearchResult(params: SearchRequestParams): Result<List<AdItemModel>>
}