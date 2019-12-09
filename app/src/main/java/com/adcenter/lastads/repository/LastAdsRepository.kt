package com.adcenter.lastads.repository

import com.adcenter.lastads.data.AdModel
import com.adcenter.lastads.data.LastAdsRequestParams
import com.adcenter.utils.Result
import kotlinx.coroutines.*

class LastAdsRepository : ILastAdsRepository {

    override suspend fun getLastAds(params: LastAdsRequestParams): Result<List<AdModel>> {
        return withContext(Dispatchers.IO) {

            delay(4000)

            suspendCancellableCoroutine<Result<List<AdModel>>> { continuation ->
                runCatching {
                    val response = testResult(params)

                    if (isActive) {
                        continuation.resume(
                            Result.Success(response)
                        ) {}
                    } else {
                        continuation.cancel()
                    }
                }.onFailure {
                    continuation.resume(Result.Error(it)) {}
                }
            }
        }
    }

    private fun testResult(params: LastAdsRequestParams): List<AdModel> {
        val response = mutableListOf<AdModel>()

        for (number in params.pageNumber * 10 until (params.pageNumber + 1) * 10) {
            response.add(AdModel(number.toString()))
        }

        return response
    }
}
