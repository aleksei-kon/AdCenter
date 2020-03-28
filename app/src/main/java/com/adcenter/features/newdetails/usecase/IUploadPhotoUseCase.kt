package com.adcenter.features.newdetails.usecase

import com.adcenter.datasource.Result
import java.io.File

interface IUploadPhotoUseCase {

    fun upload(file: File): Result<String>
}