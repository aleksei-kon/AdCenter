package com.adcenter.features.newdetails.usecase

import com.adcenter.utils.Result
import java.io.File

interface IUploadPhotoUseCase {

    fun upload(file: File): Result<String>
}