package com.adcenter.features.search.usecase

import com.adcenter.features.search.models.SearchModel
import com.adcenter.features.search.models.SearchRequestParams
import com.adcenter.entities.Result

interface ISearchUseCase {

    fun load(requestParams: SearchRequestParams): Result<SearchModel>
}