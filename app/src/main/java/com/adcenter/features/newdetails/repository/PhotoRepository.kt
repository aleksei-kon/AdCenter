package com.adcenter.features.newdetails.repository

import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.getImageUploadUrl
import com.adcenter.data.processors.PhotoProcessor
import com.adcenter.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.File

class PhotoRepository(private val processor: PhotoProcessor) : IPhotoRepository {

    override suspend fun addPhoto(file: File): Result<String> {
        return withContext(Dispatchers.IO) {
            suspendCancellableCoroutine<Result<String>> { continuation ->
                runCatching {
                    val request = NetworkDataRequest(
                        url = getImageUploadUrl(),
                        body = file
                    )

                    val response = Callable<String>()
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