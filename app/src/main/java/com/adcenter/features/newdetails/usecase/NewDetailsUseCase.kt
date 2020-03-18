package com.adcenter.features.newdetails.usecase

import com.adcenter.features.newdetails.data.NewDetailsModel
import com.adcenter.features.newdetails.data.NewDetailsRequestParams
import com.adcenter.features.newdetails.repository.INewDetailsRepository
import com.adcenter.utils.Result

class NewDetailsUseCase(private val repository: INewDetailsRepository) : INewDetailsUseCase {

    override fun upload(params: NewDetailsRequestParams): Result<NewDetailsModel> = repository.addDetails(params)
}