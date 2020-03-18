package com.adcenter.features.newdetails.repository

import com.adcenter.api.getImageUploadUrl
import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.processors.PhotoProcessor
import com.adcenter.utils.Result
import java.io.File

class PhotoRepository(private val processor: PhotoProcessor) : IPhotoRepository {

    override fun addPhoto(file: File): Result<String> =
        runCatching {
            val request = NetworkDataRequest(
                url = getImageUploadUrl(),
                body = file
            )

            val response = Callable<String>()
                .setRequest(request)
                .setProcessor(processor)
                .call()

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}