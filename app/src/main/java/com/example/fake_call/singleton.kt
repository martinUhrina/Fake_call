package com.example.fake_call

public class singleton(

) {
    private lateinit var instance : singleton
    private var record : String =""

    class singleton constructor()
    {
    }


    fun getInstance() : singleton
    {
        if(instance == null)
        {
            instance = singleton()
        }
        return instance
    }
   fun setRecord(newRecord : String)
    {
        record = newRecord
    }
    fun getRecord() : String
    {
        return record
    }
}