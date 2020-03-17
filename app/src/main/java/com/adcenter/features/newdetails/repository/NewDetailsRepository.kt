package com.adcenter.features.newdetails.repository

import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.api.getNewDetailsUrl
import com.adcenter.data.processors.NewDetailsProcessor
import com.adcenter.features.newdetails.data.NewDetailsModel
import com.adcenter.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

class NewDetailsRepository(private val processor: NewDetailsProcessor) : INewDetailsRepository {

    override suspend fun addDetails(json: String): Result<NewDetailsModel> {
        return withContext(Dispatchers.IO) {
            suspendCancellableCoroutine<Result<NewDetailsModel>> { continuation ->
                runCatching {
                    val request = NetworkDataRequest(
                        url = getNewDetailsUrl(),
                        body = json
                    )

                    val response = Callable<NewDetailsModel>()
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