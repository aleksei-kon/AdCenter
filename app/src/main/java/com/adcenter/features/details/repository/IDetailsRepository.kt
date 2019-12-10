package com.adcenter.features.details.repository

import com.adcenter.features.details.data.DetailsModel
import com.adcenter.features.details.data.DetailsRequestParams
import com.adcenter.utils.Result

interface IDetailsRepository {

    suspend fun getDetails(params: DetailsRequestParams): Result<DetailsModel>
}