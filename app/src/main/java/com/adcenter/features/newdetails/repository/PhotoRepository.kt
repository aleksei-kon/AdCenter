package com.adcenter.features.newdetails.repository

import com.adcenter.datasource.network.ImageService
import com.adcenter.entities.Result
import com.adcenter.extensions.Constants.Request.FILE_FORM_PARAM
import com.adcenter.extensions.Constants.Request.MEDIA_TYPE_IMAGE
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PhotoRepository(
    private val imageService: ImageService
) : IPhotoRepository {

    override fun addPhoto(file: File): Result<String> =
        runCatching {
            val response = imageService
                .uploadImage(imageBodyFromFile(file))
                .execute()
                .body()

            response?.let { Result.Success(it) } ?: throw IllegalArgumentException("Empty response")
        }.getOrElse {
            Result.Error(it)
        }

    private fun imageBodyFromFile(file: File): MultipartBody.Part {
        val requestFile = RequestBody.create(MediaType.parse(MEDIA_TYPE_IMAGE), file)
        val imageBody = MultipartBody.Part.createFormData(FILE_FORM_PARAM, file.name, requestFile)

        return imageBody
    }
}