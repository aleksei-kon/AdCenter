package com.adcenter.features.newdetails.usecase

import com.adcenter.entities.Result
import com.adcenter.features.newdetails.models.NewDetailsRequestParams

interface INewDetailsUseCase {

    fun upload(params: NewDetailsRequestParams): Result<Nothing?>
}