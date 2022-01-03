package com.example.fake_call

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import android.widget.TextView
import kotlin.system.exitProcess

class PickedUpCall : AppCompatActivity() {
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picked_up_call)

        val newNumber = intent.getStringExtra("number")
        findViewById<TextView>(R.id.numberAtPicked).apply { text = newNumber.toString() }
        val newName = intent.getStringExtra("name")
        findViewById<TextView>(R.id.nameAtPicked).apply { text = newName.toString() }

        val canceledCAll : ImageButton = findViewById(R.id.canceledCall)
        canceledCAll.setOnClickListener {
            moveTaskToBack(true);
            exitProcess(-1)
        }
        startTimer()
    }



    private fun startTimer()
    {
        var seconds : TextView = findViewById(R.id.seconds)
        var minutes : TextView = findViewById(R.id.minute)
        var countSec: Int = 0
        var countMin: Int = 0

        val cTimer = object : CountDownTimer(2000000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countSec++
                if (countSec == 60)
                {
                    countMin++
                    if (countMin < 10) minutes.text = "0"+countMin.toString()
                    else minutes.text = countMin.toString()
                    countSec = 0
                }
                if (countSec<10)seconds.text = "0" + countSec.toString()
                else seconds.text = countSec.toString()


            }

            override fun onFinish() {

            }

        }
       cTimer.start()
    }
}