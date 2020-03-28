package com.adcenter.features.myads.repository

import com.adcenter.datasource.api.IApi
import com.adcenter.datasource.Callable
import com.adcenter.datasource.NetworkDataRequest
import com.adcenter.datasource.processors.AdsDataProcessor
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.myads.models.MyAdsRequestParams
import com.adcenter.datasource.Result

class MyAdsRepository(
    private val processor: AdsDataProcessor,
    private val api: IApi
) : IMyAdsRepository {

    override fun getMyAds(params: MyAdsRequestParams): Result<List<AdItemModel>> =
        runCatching {
            val request = NetworkDataRequest(
                api.getMyAdsUrl(params)
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
