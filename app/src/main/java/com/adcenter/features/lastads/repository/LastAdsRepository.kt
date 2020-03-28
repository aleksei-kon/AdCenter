package com.adcenter.features.lastads.repository

import com.adcenter.datasource.api.IApi
import com.adcenter.datasource.Callable
import com.adcenter.datasource.NetworkDataRequest
import com.adcenter.datasource.processors.AdsDataProcessor
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.lastads.models.LastAdsRequestParams
import com.adcenter.datasource.Result

class LastAdsRepository(
    private val processor: AdsDataProcessor,
    private val api: IApi
) : ILastAdsRepository {

    override fun getLastAds(params: LastAdsRequestParams): Result<List<AdItemModel>> =
        runCatching {
            val request = NetworkDataRequest(
                api.getLastAdsUrl(
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
