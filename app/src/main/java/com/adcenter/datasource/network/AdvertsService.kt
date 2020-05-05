package com.adcenter.datasource.network

import com.adcenter.entities.network.NetworkAdItem
import com.adcenter.entities.network.NetworkDetailsModel
import com.adcenter.entities.network.NewDetailsModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AdvertsService {

    @POST("advert/newDetails")
    fun addNewDetails(@Body body: NewDetailsModel): Call<Void>

    @GET("advert/details")
    fun getDetails(@Query("detailsId") detailsId: Int): Call<NetworkDetailsModel>

    @GET("advert/lastAds")
    fun getLastAds(@Query("pageNumber") pageNumber: Int, @Query("isForceRefresh") isForceRefresh: Boolean): Call<List<NetworkAdItem>>

    @GET("advert/myAds")
    fun getMyAds(@Query("pageNumber") pageNumber: Int, @Query("isForceRefresh") isForceRefresh: Boolean): Call<List<NetworkAdItem>>

    @GET("advert/bookmarks")
    fun getBookmarks(@Query("pageNumber") pageNumber: Int, @Query("isForceRefresh") isForceRefresh: Boolean): Call<List<NetworkAdItem>>

    @GET("advert/adRequests")
    fun getAdRequests(@Query("pageNumber") pageNumber: Int, @Query("isForceRefresh") isForceRefresh: Boolean): Call<List<NetworkAdItem>>

    @GET("advert/search")
    fun getSearch(
        @Query("searchText") searchText: String,
        @Query("pageNumber") pageNumber: Int,
        @Query("sortType") sortType: Int,
        @Query("isForceRefresh") isForceRefresh: Boolean
    ): Call<List<NetworkAdItem>>

    @POST("advert/update")
    fun editDetails(@Body body: NewDetailsModel): Call<Void>

    @GET("advert/showHideAd")
    fun showHideAdvert(@Query("id") adId: Int): Call<Void>

    @GET("advert/addRemoveToBookmark")
    fun addDeleteBookmark(@Query("id") adId: Int): Call<Void>

    @GET("advert/delete")
    fun deleteDetails(@Query("advertId") detailsId: Int): Call<Void>
}