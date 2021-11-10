package com.example.fake_call

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fake_call.database.CallDatabase
import kotlinx.android.synthetic.main.activity_show_history.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShowHistory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_history)

        val dao = CallDatabase.getDatabase(application).dao()
        CallListActivity.layoutManager = LinearLayoutManager(this)
        GlobalScope.launch {
            CallListActivity.adapter = CallAdapter(dao.selectAllData())
        }
    }
}