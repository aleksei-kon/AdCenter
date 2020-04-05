package com.adcenter.datasource.network

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageService {

    @Multipart
    @POST("image/upload")
    fun uploadImage(@Part image: MultipartBody.Part): Call<String>
}