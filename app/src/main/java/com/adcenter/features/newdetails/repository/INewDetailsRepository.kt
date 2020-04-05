package com.adcenter.features.newdetails.repository

import com.adcenter.entities.Result
import com.adcenter.features.newdetails.models.NewDetailsRequestParams

interface INewDetailsRepository {

    fun addDetails(params: NewDetailsRequestParams): Result<Nothing?>
}