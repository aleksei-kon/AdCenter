package com.adcenter.features.details.usecase

import com.adcenter.entities.view.DetailsModel
import com.adcenter.features.details.models.DetailsRequestParams
import com.adcenter.datasource.Result

interface IDetailsUseCase {

    fun load(requestParams: DetailsRequestParams): Result<DetailsModel>
}