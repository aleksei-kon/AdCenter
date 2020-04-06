package com.adcenter.features.newdetails.usecase

import com.adcenter.entities.network.NewDetailsModel
import com.adcenter.features.newdetails.models.NewDetailsRequestParams
import com.adcenter.features.newdetails.repository.INewDetailsRepository
import com.adcenter.entities.Result

class NewDetailsUseCase(private val repository: INewDetailsRepository) : INewDetailsUseCase {

    override fun upload(params: NewDetailsRequestParams): Result<Unit> = repository.addDetails(params)
}