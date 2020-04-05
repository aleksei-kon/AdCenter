package com.adcenter.features.search.repository

import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.search.models.SearchRequestParams
import com.adcenter.entities.Result

interface ISearchRepository {

    fun getSearchResult(params: SearchRequestParams): Result<List<AdItemModel>>
}