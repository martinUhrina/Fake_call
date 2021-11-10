package com.example.fake_call.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fake_call.CallData
import com.example.fake_call.dao.DAO

@Database(
        entities = arrayOf(CallData::class),
        version = 2
)
abstract class CallDatabase:RoomDatabase() {
    abstract fun dao():DAO

    companion object{
        private var DATABASE: CallDatabase? = null

        fun getDatabase(context: Context): CallDatabase{
            return DATABASE ?: synchronized(this){
                val database = Room.databaseBuilder(
                        context.applicationContext,
                        CallDatabase::class.java,
                        "myCallDatabase"
                ).fallbackToDestructiveMigration()
                        .build()
                DATABASE = database
                return database
            }

        }
    }
}