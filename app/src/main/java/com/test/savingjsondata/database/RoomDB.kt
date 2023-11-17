package com.test.savingjsondata.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LocalEntity::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    //    A method to fetch the dao instance
    abstract fun getDao(): DAO

    companion object {
        //        "INSTANCE" for using the current instance of the database
        //        and "Volatile" so that it is never cached hence, all read and write operations
        //        will be performed in the main memory
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getDatabase(context: Context): RoomDB {
//            putting the code inside "synchronized" container will avoid multiple execute at a
//            time, also will make sure that the "INSTANCE" gets initialized only once
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "photos_data"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}