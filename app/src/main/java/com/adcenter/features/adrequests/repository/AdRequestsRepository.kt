package com.adcenter.features.adrequests.repository

import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.getAdRequestsUrl
import com.adcenter.data.processors.AdsDataProcessor
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.adrequests.data.AdRequestsParams
import com.adcenter.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

class AdRequestsRepository : IAdRequestsRepository {

    private val processor = AdsDataProcessor()

    override suspend fun getAdRequests(params: AdRequestsParams): Result<List<AdItemModel>> {
        return withContext(Dispatchers.IO) {
            suspendCancellableCoroutine<Result<List<AdItemModel>>> { continuation ->
                runCatching {
                    val request = NetworkDataRequest(getAdRequestsUrl(params))

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