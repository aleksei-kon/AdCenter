package com.adcenter.features.adrequests.repository

import com.adcenter.api.IApi
import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.processors.AdsDataProcessor
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.adrequests.data.AdRequestsParams
import com.adcenter.utils.Result
import javax.inject.Inject

class AdRequestsRepository(private val processor: AdsDataProcessor) : IAdRequestsRepository {

    @Inject
    lateinit var api: IApi

    init {
        Injector.appComponent.inject(this)
    }

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
