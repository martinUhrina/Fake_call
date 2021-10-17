package com.example.fake_call

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time


@Entity()
data class CallData(
    @PrimaryKey(autoGenerate = true) var id:Int,
    var meno:String,
    var cislo:String
)
{


}


