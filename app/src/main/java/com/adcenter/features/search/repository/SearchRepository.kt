package com.adcenter.features.search.repository

import com.adcenter.entities.AdItemModel
import com.adcenter.features.search.data.SearchRequestParams
import com.adcenter.utils.Result
import kotlinx.coroutines.*

class SearchRepository : ISearchRepository {

    override suspend fun getSearchResult(params: SearchRequestParams): Result<List<AdItemModel>> {
        return withContext(Dispatchers.IO) {

            delay(4000)

            suspendCancellableCoroutine<Result<List<AdItemModel>>> { continuation ->
                runCatching {
                    val response = testResult(params)

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

    private fun testResult(params: SearchRequestParams): List<AdItemModel> {
        val response = mutableListOf<AdItemModel>()

        for (number in params.pageNumber * 10 until (params.pageNumber + 1) * 10) {
            val url = "https://data.whicdn.com/images/322304619/original.jpg"
            val title = "Search title for ${params.searchText} $number"
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