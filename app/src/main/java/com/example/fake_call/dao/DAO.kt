package com.example.fake_call.dao

import androidx.room.*
import com.example.fake_call.CallData


@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertData(call_data:CallData)

    @Query("SELECT * FROM CallData")
    fun selectAllData():List<CallData>

    @Delete
    fun deleteOneCall(callData : CallData)

    @Query("DELETE FROM CallData")
    fun deleteAllCall()




}