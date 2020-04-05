package com.adcenter.features.newdetails.usecase

import com.adcenter.entities.Result
import java.io.File

interface IUploadPhotoUseCase {

    fun upload(file: File): Result<String>
}