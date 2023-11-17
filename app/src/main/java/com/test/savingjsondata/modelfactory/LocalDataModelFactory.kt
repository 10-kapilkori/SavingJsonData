package com.test.savingjsondata.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.savingjsondata.repository.LocalDataRepository
import com.test.savingjsondata.viewmodel.LocalDataViewModel

class LocalDataModelFactory(private val repository: LocalDataRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocalDataViewModel::class.java)) {
            return LocalDataViewModel(repository) as T
        }

        throw IllegalArgumentException("Invalid View Model Class")
    }
}