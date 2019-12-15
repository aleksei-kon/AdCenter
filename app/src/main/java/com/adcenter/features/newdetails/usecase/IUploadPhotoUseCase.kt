package com.adcenter.features.newdetails.usecase

import com.adcenter.utils.Result
import java.io.File

interface IUploadPhotoUseCase {

    suspend fun upload(file: File): Result<String>
}