package com.test.savingjsondata.repository

import com.test.savingjsondata.retrofit.API

class PhotosRepository(private val api: API) {

    suspend fun getPhotos() = api.getPhotos()
}