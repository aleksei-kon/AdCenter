package com.adcenter.features.newdetails.usecase

import com.adcenter.features.newdetails.data.NewDetailsModel
import com.adcenter.features.newdetails.data.NewDetailsRequestParams
import com.adcenter.utils.Result

interface INewDetailsUseCase {

    fun upload(params: NewDetailsRequestParams): Result<NewDetailsModel>
}