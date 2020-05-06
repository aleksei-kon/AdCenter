package com.adcenter.features.adrequests.usecase

import com.adcenter.entities.Result
import com.adcenter.features.adrequests.models.AdRequestsModel
import com.adcenter.features.adrequests.models.AdRequestsParams
import com.adcenter.features.adrequests.repository.IAdRequestsRepository

class AdRequestsUseCase(private val repository: IAdRequestsRepository) : IAdRequestsUseCase {

    override fun load(requestParams: AdRequestsParams): Result<AdRequestsModel> =
        when (val result = repository.getAdRequests(requestParams)) {
            is Result.Success -> Result.Success(AdRequestsModel(result.value))
            is Result.Error -> Result.Error(result.exception)
        }

    override fun clearDb() {
        repository.clearDb()
    }
}