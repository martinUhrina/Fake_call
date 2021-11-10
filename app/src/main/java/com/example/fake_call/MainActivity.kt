package com.example.fake_call

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val navController = this.findNavController(R.id.navHostFragment)                        //vytvorenie controlera, a nastavenie mu fragment
        NavigationUI.setupActionBarWithNavController(this,navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
        return navController.navigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id : Int = item.itemId
        if(id == R.id.history_fragment)
        {
            val StartHistory = Intent(this
                , ShowHistory::class.java)

            startActivity(StartHistory)

        }

        return super.onOptionsItemSelected(item)
    }
}

