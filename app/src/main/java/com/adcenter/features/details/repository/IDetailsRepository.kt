package com.adcenter.features.details.repository

import com.adcenter.entities.view.DetailsModel
import com.adcenter.features.details.data.DetailsRequestParams
import com.adcenter.utils.Result

interface IDetailsRepository {

    fun getDetails(params: DetailsRequestParams): Result<DetailsModel>
}