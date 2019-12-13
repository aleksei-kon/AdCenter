package com.adcenter.features.lastads.repository

import com.adcenter.entities.AdItemModel
import com.adcenter.features.lastads.data.LastAdsRequestParams
import com.adcenter.utils.Result
import kotlinx.coroutines.*

class LastAdsRepository : ILastAdsRepository {

    override suspend fun getLastAds(params: LastAdsRequestParams): Result<List<AdItemModel>> {
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

    private fun testResult(params: LastAdsRequestParams): List<AdItemModel> {
        val response = mutableListOf<AdItemModel>()

        for (number in params.pageNumber * 10 until (params.pageNumber + 1) * 10) {
            val url = "https://data.whicdn.com/images/322304619/original.jpg"
            val title = "Bokmarks title $number"
            val place = "Place $number"
            val price = "${(1..10000).random()} byn"
            val views = (1..1000).random()
            val id = "id:$number"

            response.add(
                AdItemModel(
                    id = id,
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
