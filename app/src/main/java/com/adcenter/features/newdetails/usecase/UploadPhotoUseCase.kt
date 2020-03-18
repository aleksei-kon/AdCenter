package com.adcenter.features.newdetails.usecase

import com.adcenter.features.newdetails.repository.IPhotoRepository
import com.adcenter.utils.Result
import java.io.File

class UploadPhotoUseCase(private val repository: IPhotoRepository) : IUploadPhotoUseCase {

    override fun upload(file: File): Result<String> = repository.addPhoto(file)
}