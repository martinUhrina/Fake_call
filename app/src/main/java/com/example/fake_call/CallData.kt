package com.example.fake_call

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time


@Entity(tableName = "callDataHistory")
data class CallData(
    @PrimaryKey val id:Int,
    val meno:String,
    val cislo:String,
    val cas:Time
)
{


}


