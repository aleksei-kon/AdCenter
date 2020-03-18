package com.adcenter.features.newdetails.repository

import com.adcenter.features.newdetails.data.NewDetailsModel
import com.adcenter.features.newdetails.data.NewDetailsRequestParams
import com.adcenter.utils.Result

interface INewDetailsRepository {

    fun addDetails(params: NewDetailsRequestParams): Result<NewDetailsModel>
}