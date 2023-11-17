package com.test.savingjsondata.retrofit

import com.test.savingjsondata.pojos.Photos
import retrofit2.Response
import retrofit2.http.GET

interface API {
    @GET("/photos")
    suspend fun getPhotos(): Response<List<Photos>>
}