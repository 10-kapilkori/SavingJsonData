package com.test.savingjsondata.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.savingjsondata.repository.PhotosRepository
import com.test.savingjsondata.viewmodel.PhotosViewModel

class PhotoModelFactory(private val repo: PhotosRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotosViewModel::class.java)) {
            return PhotosViewModel(repo) as T
        }

        throw IllegalArgumentException("Invalid View Model Class")
    }
}