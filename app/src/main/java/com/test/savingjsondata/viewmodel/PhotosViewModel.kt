package com.test.savingjsondata.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.savingjsondata.pojos.Photos
import com.test.savingjsondata.repository.PhotosRepository
import com.test.savingjsondata.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotosViewModel(private val photoRepo: PhotosRepository) : ViewModel() {

    val photoLiveData: MutableLiveData<Resource<List<Photos>>> = MutableLiveData()

    fun getPhotos() {
        CoroutineScope(Dispatchers.IO).launch {
            photoLiveData.postValue(Resource.loading(null))

            try {
                photoLiveData.postValue(Resource.success(photoRepo.getPhotos().body()))
            } catch (e: Exception) {
                photoLiveData.postValue(Resource.failure(e.message.toString(), null))
            }
        }
    }
}