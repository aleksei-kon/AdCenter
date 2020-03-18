package com.adcenter.features.lastads.repository

import com.adcenter.api.IApi
import com.adcenter.app.App
import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.processors.AdsDataProcessor
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.lastads.data.LastAdsRequestParams
import com.adcenter.utils.Result
import javax.inject.Inject

class LastAdsRepository(private val processor: AdsDataProcessor) : ILastAdsRepository {

    @Inject
    lateinit var api: IApi

    init {
        App.appComponent.inject(this)
    }

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
