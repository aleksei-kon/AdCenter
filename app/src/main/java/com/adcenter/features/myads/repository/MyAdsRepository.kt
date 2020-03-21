package com.adcenter.features.myads.repository

import com.adcenter.api.IApi
import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.processors.AdsDataProcessor
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.myads.data.MyAdsRequestParams
import com.adcenter.utils.Result

class MyAdsRepository(
    private val processor: AdsDataProcessor,
    private val api: IApi
) : IMyAdsRepository {

    override fun getMyAds(params: MyAdsRequestParams): Result<List<AdItemModel>> =
        runCatching {
            val request = NetworkDataRequest(api.getMyAdsUrl(params))

            val response = Callable<List<AdItemModel>>()
                .setRequest(request)
                .setProcessor(processor)
                .call()

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}
