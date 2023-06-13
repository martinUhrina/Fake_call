package com.example.fake_call


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity()
data class CallData(
        @PrimaryKey(autoGenerate = true) var id: Int? = null,
        var meno: String,
        var cislo: String,
        var currentDate: String,

)
{

}


