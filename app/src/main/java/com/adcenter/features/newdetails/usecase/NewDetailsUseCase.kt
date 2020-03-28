package com.adcenter.features.newdetails.usecase

import com.adcenter.features.newdetails.models.NewDetailsModel
import com.adcenter.features.newdetails.models.NewDetailsRequestParams
import com.adcenter.features.newdetails.repository.INewDetailsRepository
import com.adcenter.datasource.Result

class NewDetailsUseCase(private val repository: INewDetailsRepository) : INewDetailsUseCase {

    override fun upload(params: NewDetailsRequestParams): Result<NewDetailsModel> = repository.addDetails(params)
}