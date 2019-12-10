package com.adcenter.myads.repository

import com.adcenter.entities.AdItemModel
import com.adcenter.myads.data.MyAdsRequestParams
import com.adcenter.utils.Result
import kotlinx.coroutines.*

class MyAdsRepository : IMyAdsRepository {

    override suspend fun getMyAds(params: MyAdsRequestParams): Result<List<AdItemModel>> {
        return withContext(Dispatchers.IO) {

            delay(4000)

            suspendCancellableCoroutine<Result<List<AdItemModel>>> { continuation ->
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

    private fun testResult(params: MyAdsRequestParams): List<AdItemModel> {
        val response = mutableListOf<AdItemModel>()

        for (number in params.pageNumber * 10 until (params.pageNumber + 1) * 10) {
            val url = "https://data.whicdn.com/images/322304619/original.jpg"
            val title = "Bokmarks title $number"
            val place = "Place $number"
            val price = "${(1..10000).random()} byn"
            val views = (1..1000).random()

            response.add(
                AdItemModel(
                    photoUrl = url,
                    title = title,
                    price = price,
                    place = place,
                    views = views
                )
            )
        }

        return response
    }
}