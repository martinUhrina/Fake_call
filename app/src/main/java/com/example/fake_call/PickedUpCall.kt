package com.example.fake_call

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import kotlin.system.exitProcess

class PickedUpCall : AppCompatActivity() {
    var data : String = ""
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picked_up_call)


        val sharedPreferences : SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedString : String = sharedPreferences.getString("STRING_KEY", null).toString()
 //       Toast.makeText(this, savedString, Toast.LENGTH_SHORT).show()

        val contextWrapper : ContextWrapper = ContextWrapper(applicationContext)
        val musicDirectory : File? = contextWrapper.getExternalFilesDir(Environment.getExternalStorageDirectory().toString())
        // val file : File? = File(musicDirectory, "/" + currentDateLocal + ".mp3")
        val file : File? = File(musicDirectory, "/" + savedString)
    /*    if (file != null) {
            Toast.makeText(this, file.path, Toast.LENGTH_SHORT).show()
        }
*/
        var mediaPlayer : MediaPlayer = MediaPlayer()
        if (file != null) {
            mediaPlayer.setDataSource(file.path)
            mediaPlayer.prepare()
            mediaPlayer.start()
        }




        var soundPool : SoundPool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            var audioAttributes = AudioAttributes.Builder().
                setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).
                setUsage(AudioAttributes.USAGE_GAME).build()
            soundPool = SoundPool.Builder().
                setMaxStreams(2).
                setAudioAttributes(audioAttributes).build()
        }
        else soundPool = SoundPool(2,AudioManager.STREAM_MUSIC,0)

  //      var sound : Int = soundPool.load(this,savedString,1)
   //     soundPool.play(sound, 1F, 1F,1,1, 1F)






      //  val filePath = Environment.getExternalStorageDirectory().toString() + savedString




    /*    val file = File(
                this.getDir("Music", MODE_PRIVATE),
                savedString
        )
        var mediaPlayer1 = MediaPlayer.create(this, Uri.fromFile(file ))
        mediaPlayer1.start()
*/
//        var path  : String = Environment.getExternalStorageDirectory().toString()+"/Tue Feb 03 20:48:20 GMT+01:00 2022.mp3"
     //   var cdjuaios = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";


        /*
             if (savedString != null){
                 var mediaPLayer = MediaPlayer()
                 mediaPLayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                 mediaPLayer.setDataSource(savedString)
                 mediaPLayer.prepare()
                 mediaPLayer.start()
             }
*/
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