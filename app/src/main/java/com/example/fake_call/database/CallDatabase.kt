package com.example.fake_call.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.fake_call.CallData
import com.example.fake_call.dao.DAO
/*
@Database(
        entities = arrayOf(CallData::class),
        version = 1
)
abstract class CallDatabase {
    abstract fun dao():DAO

    companion object{
        private var DATABASE: CallDatabase? = null

        fun getDatabase(context: Context): CallDatabase{
            return DATABASE ?: synchronized(this){
                val database = Room.databaseBuilder(
                        context.applicationContext,
                        CallDatabase::class.java,
                        "myCallDatabase"
                ).build()
                DATABASE = database
                return database
            }

        }
    }
}*/