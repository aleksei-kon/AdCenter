package com.adcenter.myads.repository

import com.adcenter.entities.AdModel
import com.adcenter.myads.data.MyAdsRequestParams
import com.adcenter.utils.Result
import kotlinx.coroutines.*

class MyAdsRepository : IMyAdsRepository {

    override suspend fun getMyAds(params: MyAdsRequestParams): Result<List<AdModel>> {
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

    private fun testResult(params: MyAdsRequestParams): List<AdModel> {
        val response = mutableListOf<AdModel>()

        for (number in params.pageNumber * 10 until (params.pageNumber + 1) * 10) {
            response.add(AdModel("$number my ads"))
        }

        return response
    }
}