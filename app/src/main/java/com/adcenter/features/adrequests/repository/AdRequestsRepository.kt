package com.adcenter.features.adrequests.repository

import com.adcenter.datasource.api.IApi
import com.adcenter.datasource.Callable
import com.adcenter.datasource.NetworkDataRequest
import com.adcenter.datasource.processors.AdsDataProcessor
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.adrequests.models.AdRequestsParams
import com.adcenter.datasource.Result

class AdRequestsRepository(
    private val processor: AdsDataProcessor,
    private val api: IApi
) : IAdRequestsRepository {

    override fun getAdRequests(params: AdRequestsParams): Result<List<AdItemModel>> =
        runCatching {
            val request = NetworkDataRequest(
                api.getAdRequestsUrl(
                    params
                )
            )

            val response = Callable<List<AdItemModel>>()
                .setRequest(request)
                .setProcessor(processor)
                .call()

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}
