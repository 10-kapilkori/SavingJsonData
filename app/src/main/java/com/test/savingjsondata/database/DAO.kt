package com.test.savingjsondata.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DAO {

    @Insert
    suspend fun insertData(localEntity: LocalEntity)

    @Query("SELECT * FROM Photos")
    suspend fun fetchData(): List<LocalEntity>
}