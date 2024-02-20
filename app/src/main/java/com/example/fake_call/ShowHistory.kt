package com.example.fake_call

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fake_call.database.CallDatabase
import kotlinx.android.synthetic.main.activity_show_history.*
import kotlinx.android.synthetic.main.activity_show_history.view.*
import kotlinx.coroutines.*

class ShowHistory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_history)
        val actionBar = supportActionBar
        actionBar!!.title = ""


        loadRecyclerView()
        refreshApp()

    }

    private fun loadRecyclerView() {
        val dao = CallDatabase.getDatabase(application).dao()
        CallListActivity.layoutManager = LinearLayoutManager(this)
        GlobalScope.launch {
            CallListActivity.adapter = CallAdapter(dao.selectAllData())
        }
    }

    private fun refreshApp() {
        SwipeRefreshLayout.setOnRefreshListener(){
            loadRecyclerView()
            SwipeRefreshLayout.isRefreshing = false
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        var inflater = menuInflater
        inflater.inflate(R.menu.menu_delete,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.deleteAll)
        {
            chosse()
        }
        else {
            goBack()
        }
        return true
    }

    private fun goBack() {
        val mainActivityLaunch = Intent(this, MainActivity::class.java)
        startActivity(mainActivityLaunch)
    }

    private fun chosse() {
        var builder =  AlertDialog.Builder(this)
        .setTitle("DELETE")
        .setMessage("Do you want delete all data?")
        builder?.setPositiveButton("Yes"){dialogInterface: DialogInterface, i: Int -> deletedata()}
        builder?.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int -> }?.show()

        }

    private fun deletedata() {
        val database = CallDatabase.getDatabase(this)
        val dao = database.dao()
        CallListActivity.layoutManager = LinearLayoutManager(this)
      //  CoroutineScope(Dispatchers.IO).launch {
         GlobalScope.launch {
             dao.deleteAllCall()
             CoroutineScope(Dispatchers.IO).launch {
                 CallAdapter(dao.selectAllData()).clearItems()
             }

             withContext(Dispatchers.Main)
            {
                Log.i("ShowHistory","SME PRED CLEARITEMS V Dispatcherovi")
            }
         }

        Toast.makeText(this@ShowHistory, "Call history was successful deleted", Toast.LENGTH_SHORT).show()
        }
    }


