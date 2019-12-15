package com.adcenter.features.newdetails.repository

import com.adcenter.utils.Result
import java.io.File

interface IPhotoRepository {

    suspend fun addPhoto(file: File): Result<String>
}