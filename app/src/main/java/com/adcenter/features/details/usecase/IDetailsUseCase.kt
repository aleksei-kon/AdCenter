package com.adcenter.features.details.usecase

import com.adcenter.entities.view.DetailsModel
import com.adcenter.features.details.data.DetailsRequestParams
import com.adcenter.utils.Result

interface IDetailsUseCase {

    fun load(requestParams: DetailsRequestParams): Result<DetailsModel>
}