package com.adcenter.features.details.repository

import com.adcenter.entities.view.DetailsModel
import com.adcenter.features.details.models.DetailsRequestParams
import com.adcenter.datasource.Result

interface IDetailsRepository {

    fun getDetails(params: DetailsRequestParams): Result<DetailsModel>
}