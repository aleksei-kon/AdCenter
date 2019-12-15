package com.adcenter.features.newdetails.usecase

import com.adcenter.features.newdetails.repository.IPhotoRepository
import com.adcenter.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.io.File

class UploadPhotoUseCase(private val repository: IPhotoRepository) : IUploadPhotoUseCase {

    override suspend fun upload(file: File): Result<String> {
        return coroutineScope {
            val adsAsync = async { repository.addPhoto(file) }
            adsAsync.await()
        }
    }
}