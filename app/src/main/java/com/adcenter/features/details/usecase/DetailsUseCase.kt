package com.adcenter.features.details.usecase

import com.adcenter.entities.view.DetailsModel
import com.adcenter.features.details.models.DetailsRequestParams
import com.adcenter.features.details.repository.IDetailsRepository
import com.adcenter.datasource.Result

class DetailsUseCase(private val repository: IDetailsRepository) : IDetailsUseCase {

    override fun load(requestParams: DetailsRequestParams): Result<DetailsModel> =
        repository.getDetails(requestParams)
}