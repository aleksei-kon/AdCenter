package com.adcenter.features.search.usecase

import com.adcenter.features.search.data.SearchModel
import com.adcenter.features.search.data.SearchRequestParams
import com.adcenter.utils.Result

interface ISearchUseCase {

    fun load(requestParams: SearchRequestParams): Result<SearchModel>
}