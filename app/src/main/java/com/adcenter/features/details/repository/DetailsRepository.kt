package com.adcenter.features.details.repository

import com.adcenter.features.details.data.DetailsModel
import com.adcenter.features.details.data.DetailsRequestParams
import com.adcenter.utils.Result
import kotlinx.coroutines.*

class DetailsRepository : IDetailsRepository {

    override suspend fun getDetails(params: DetailsRequestParams): Result<DetailsModel> {
        return withContext(Dispatchers.IO) {

            delay(4000)

            suspendCancellableCoroutine<Result<DetailsModel>> { continuation ->
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

    private fun testResult(params: DetailsRequestParams): DetailsModel {
        return DetailsModel(
            photos = listOf(
                "data:image/jpeg;base64,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",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_SIymV2Yd1X8nxsOfPEy2EYOy0EslOjqk7zGkr1Lc76nfJG02&s",
                "https://images.pexels.com/photos/45201/kitty-cat-kitten-pet-45201.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "https://images2.minutemediacdn.com/image/upload/c_crop,h_1193,w_2121,x_0,y_64/f_auto,q_auto,w_1100/v1565279671/shape/mentalfloss/578211-gettyimages-542930526.jpg",
                "https://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/article_thumbnails/reference_guide/cats_and_excessive_meowing_ref_guide/1800x1200_cats_and_excessive_meowing_ref_guide.jpg"
            ),
            isBookmark = true,
            title = "Title ${params.detailsId}",
            price = "100 byn",
            location = "Grodno",
            date = "12.12.2019",
            views = 115,
            synopsis = "It is a long established fact that a reader will be distracted by the readable content of a" +
                    " page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal" +
                    " distribution of letters, as opposed to using 'Content here, content here', making it look like readable" +
                    " English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default" +
                    " model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. ",
            username = "Aleksei",
            phone = "375292462199"
        )
    }
}

