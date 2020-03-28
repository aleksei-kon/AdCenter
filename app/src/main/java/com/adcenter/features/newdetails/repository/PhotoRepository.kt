package com.adcenter.features.newdetails.repository

import com.adcenter.datasource.api.IApi
import com.adcenter.datasource.Callable
import com.adcenter.datasource.NetworkDataRequest
import com.adcenter.datasource.processors.PhotoProcessor
import com.adcenter.datasource.Result
import java.io.File

class PhotoRepository(
    private val processor: PhotoProcessor,
    private val api: IApi
) : IPhotoRepository {

    override fun addPhoto(file: File): Result<String> =
        runCatching {
            val request = NetworkDataRequest(
                url = api.getImageUploadUrl(),
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