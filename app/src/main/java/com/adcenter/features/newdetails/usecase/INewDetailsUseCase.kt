package com.adcenter.features.newdetails.usecase

import com.adcenter.features.newdetails.data.NewDetailsModel
import com.adcenter.utils.Result

interface INewDetailsUseCase {

    suspend fun upload(json: String): Result<NewDetailsModel>
}