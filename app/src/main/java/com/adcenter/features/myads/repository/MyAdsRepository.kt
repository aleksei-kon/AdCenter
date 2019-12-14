package com.adcenter.features.myads.repository

import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.getMyAdsUrl
import com.adcenter.data.processors.AdsDataProcessor
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.myads.data.MyAdsRequestParams
import com.adcenter.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

class MyAdsRepository : IMyAdsRepository {

    private val processor = AdsDataProcessor()

    override suspend fun getMyAds(params: MyAdsRequestParams): Result<List<AdItemModel>> {
        return withContext(Dispatchers.IO) {
            suspendCancellableCoroutine<Result<List<AdItemModel>>> { continuation ->
                runCatching {
                    val request = NetworkDataRequest(getMyAdsUrl(params))

                    val response = Callable<List<AdItemModel>>()
                        .setRequest(request)
                        .setProcessor(processor)
                        .call()

                    if (isActive) {
                        continuation.resume(Result.Success(response)) {}
                    } else {
                        continuation.cancel()
                    }
                }.onFailure {
                    continuation.resume(Result.Error(it)) {}
                }
            }
        }
    }
}