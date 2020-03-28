package com.adcenter.features.newdetails.usecase

import com.adcenter.features.newdetails.models.NewDetailsModel
import com.adcenter.features.newdetails.models.NewDetailsRequestParams
import com.adcenter.datasource.Result

interface INewDetailsUseCase {

    fun upload(params: NewDetailsRequestParams): Result<NewDetailsModel>
}