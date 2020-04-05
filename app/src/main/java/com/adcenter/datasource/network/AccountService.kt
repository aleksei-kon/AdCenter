package com.adcenter.datasource.network

import com.adcenter.entities.network.CredentialsModel
import com.adcenter.entities.network.NetworkAppConfigModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountService {

    @POST("account/register")
    fun registerUser(@Body body: CredentialsModel): Call<NetworkAppConfigModel>

    @POST("account/login")
    fun loginUser(@Body body: CredentialsModel): Call<NetworkAppConfigModel>
}