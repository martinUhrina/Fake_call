package com.example.fake_call.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fake_call.CallData


@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertData(call_data:CallData)

    @Query("SELECT * FROM callDataHistory")
    fun selectAllData():List<CallData>
}