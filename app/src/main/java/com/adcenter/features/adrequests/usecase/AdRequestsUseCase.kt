package com.adcenter.features.adrequests.usecase

import com.adcenter.features.adrequests.models.AdRequestsModel
import com.adcenter.features.adrequests.models.AdRequestsParams
import com.adcenter.features.adrequests.repository.IAdRequestsRepository
import com.adcenter.entities.Result

class AdRequestsUseCase(private val repository: IAdRequestsRepository) : IAdRequestsUseCase {

    override fun load(requestParams: AdRequestsParams): Result<AdRequestsModel> =
        Result.Success(loadModel(requestParams))

    override fun clearDb() {
        repository.clearDb()
    }

    private fun loadModel(requestParams: AdRequestsParams): AdRequestsModel {
        val ads = when (val result = repository.getAdRequests(requestParams)) {
            is Result.Success -> result.value
            is Result.Error -> emptyList()
        }

        return AdRequestsModel(ads)
    }
}