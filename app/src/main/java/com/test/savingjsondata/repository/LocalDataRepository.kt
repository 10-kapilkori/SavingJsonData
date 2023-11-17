package com.test.savingjsondata.repository

import com.test.savingjsondata.database.DAO
import com.test.savingjsondata.database.LocalEntity

class LocalDataRepository(private val dao: DAO) {

    suspend fun getLocalData() = dao.fetchData()

    suspend fun insertData(localEntity: LocalEntity) = dao.insertData(localEntity)
}