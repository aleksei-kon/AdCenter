package com.adcenter.features.newdetails.repository

import com.adcenter.features.newdetails.models.NewDetailsModel
import com.adcenter.features.newdetails.models.NewDetailsRequestParams
import com.adcenter.datasource.Result

interface INewDetailsRepository {

    fun addDetails(params: NewDetailsRequestParams): Result<NewDetailsModel>
}