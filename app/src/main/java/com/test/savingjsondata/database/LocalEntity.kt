package com.test.savingjsondata.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Photos")
data class LocalEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var url: String,
    var thumbUrl: String
)