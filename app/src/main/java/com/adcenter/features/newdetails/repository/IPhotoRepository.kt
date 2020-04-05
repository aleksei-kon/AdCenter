package com.adcenter.features.newdetails.repository

import com.adcenter.entities.Result
import java.io.File

interface IPhotoRepository {

    fun addPhoto(file: File): Result<String>
}