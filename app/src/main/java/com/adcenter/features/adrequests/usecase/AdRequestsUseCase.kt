package com.adcenter.features.adrequests.usecase

import com.adcenter.features.adrequests.data.AdRequestsModel
import com.adcenter.features.adrequests.data.AdRequestsParams
import com.adcenter.features.adrequests.repository.IAdRequestsRepository
import com.adcenter.utils.Constants
import com.adcenter.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class AdRequestsUseCase(private val repository: IAdRequestsRepository) : IAdRequestsUseCase {

    override suspend fun load(requestParams: AdRequestsParams): Result<AdRequestsModel> =
        Result.Success(loadModel(requestParams))

    private suspend fun loadModel(requestParams: AdRequestsParams): AdRequestsModel {
        return coroutineScope {
            val adsAsync = async {
                delay(Constants.REQUEST_DELAY)
                repository.getAdRequests(requestParams)
            }
            val adsResult = adsAsync.await()

            val ads = when (adsResult) {
                is Result.Success -> adsResult.value
                is Result.Error -> emptyList()
            }

            AdRequestsModel(ads)
        }
    }
}