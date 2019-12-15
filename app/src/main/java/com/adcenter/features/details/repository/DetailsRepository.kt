package com.adcenter.features.details.repository

import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.getDetailsUrl
import com.adcenter.data.processors.DetailsProcessor
import com.adcenter.entities.view.DetailsModel
import com.adcenter.features.details.data.DetailsRequestParams
import com.adcenter.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

class DetailsRepository(private val processor: DetailsProcessor) : IDetailsRepository {

    override suspend fun getDetails(params: DetailsRequestParams): Result<DetailsModel> {
        return withContext(Dispatchers.IO) {
            suspendCancellableCoroutine<Result<DetailsModel>> { continuation ->
                runCatching {
                    val request = NetworkDataRequest(getDetailsUrl(params))

                    val response = Callable<DetailsModel>()
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

