package com.test.savingjsondata.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.savingjsondata.database.LocalEntity
import com.test.savingjsondata.repository.LocalDataRepository
import com.test.savingjsondata.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocalDataViewModel(private val repository: LocalDataRepository) : ViewModel() {

    val localDataLiveData: MutableLiveData<Resource<List<LocalEntity>>> = MutableLiveData()
    val localDataList: ArrayList<LocalEntity> = arrayListOf()

    fun fetchLocalData() {
        CoroutineScope(Dispatchers.IO).launch {
            localDataLiveData.postValue(Resource.success(repository.getLocalData()))
        }
    }

    fun insertLocalData(localEntity: LocalEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertData(localEntity)
        }
    }
}