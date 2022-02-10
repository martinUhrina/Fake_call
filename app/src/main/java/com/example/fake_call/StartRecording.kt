package com.example.fake_call

import android.content.*
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.activity_show_history.*
import java.io.File
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


//@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class StartRecording : AppCompatActivity() {
    var MICROPHONE_PERMISSION_CODE = 200;
    var nameOfRecord = MutableLiveData<String>()
    var help = MutableLiveData<Boolean>()
    var mediaRecorder : MediaRecorder = MediaRecorder()
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_recording)

        var recording : Button = findViewById(R.id.recoding)
        var stop : Button = findViewById(R.id.stop)
        var play : Button = findViewById(R.id.play)




        //       var hellpButtona2 : Button = findViewById(R.id.button3)


      /*  hellpButtona.setOnClickListener {


            val sharedPreferences : SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val editor :SharedPreferences.Editor = sharedPreferences.edit()
            editor.apply(){
                putString("STRING_KEY","JABLKO")
            }.apply()
            Toast.makeText(this, "Data saved",Toast.LENGTH_SHORT).show()
        }
        hellpButtona2.setOnClickListener {
            val sharedPreferences : SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val savedString : String = sharedPreferences.getString("STRING_KEY",null).toString()
            Toast.makeText(this, savedString,Toast.LENGTH_SHORT).show()
        }
*/












        val actionbar = supportActionBar
        actionbar!!.title = "Records"
        actionbar.setDisplayHomeAsUpEnabled(true)


        stop.isEnabled = false
        var path = String()

  //      Toast.makeText(this,path,Toast.LENGTH_SHORT).show()




        val mediaPlayer : MediaPlayer = MediaPlayer()

        var isRecorded : Boolean = false
        var listView : ListView = findViewById(R.id.ListView)

        if (checkMicrophone())
        {
            givePermission()
        }

        var mp3file : ArrayList<String> = ArrayList()

        var skuska : File = File("/storage/emulated/0/Android/data/com.example.fake_call/files/storage/emulated/0/Tue Jan 18 21:34:07 GMT+01:00 2022.mp3")
        var skuska2 : File = File("/storage/emulated/0/Android/data/com.example.fake_call/files/storage/emulated/0/Tue Jan 18 21:38:42 GMT+01:00 2022.mp3")
        var skuska3 : ArrayList<File> = ArrayList()
        skuska.deleteOnExit()

        File("/storage/emulated/0/Android/data/com.example.fake_call/files/storage/emulated/0/").walkBottomUp().forEach {
            skuska3.add(it)
        }


        for (f in skuska3)
        {
            mp3file.add(f.name)
        }

        var adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, mp3file)
        listView.adapter = adapter;

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
    //        this.actualRecord.value = adapter.getItem(position).toString()
              Toast.makeText(this, adapter.getItem(position).toString(), Toast.LENGTH_SHORT).show()
       //     this.actualRecord.value = givePath().toString()
            sendData(adapter.getItem(position).toString())

        }


        recording.setOnClickListener {
            builder()

     //      Toast.makeText(this, path, Toast.LENGTH_SHORT).show()
         /*   mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
       //     mediaRecorder.setOutputFile("/storage/emulated/0/Android/data/com.example.fake_call/files/storage/emulated/0/kokos.mp3")
            mediaRecorder.setOutputFile(path)

            mediaRecorder.prepare()
            mediaRecorder.start()
  //          Toast.makeText(this, path,Toast.LENGTH_SHORT).show()
*/
            //     Toast.makeText(this,"The recording started",Toast.LENGTH_SHORT).show()
            stop.isEnabled = true
         /*   mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
            mediaRecorder.setOutputFile(path)
            mediaRecorder.prepare()
            mediaRecorder.start()
            stop.isEnabled = true
            isRecorded = true
            Toast.makeText(this,"The recording started",Toast.LENGTH_SHORT).show()
       */ }
        stop.setOnClickListener {
            mediaRecorder.stop()
            stop.isEnabled = false
            Toast.makeText(this, "The recording stopped", Toast.LENGTH_SHORT).show()

            /*stop.isEnabled = false
            if (isRecorded)
            {
                mediaRecorder.stop()
                mediaRecorder.reset()
                mediaRecorder.release()

                Toast.makeText(this, "The recording stopped", Toast.LENGTH_SHORT).show()
            }*/
        }
        play.setOnClickListener {
            var mp : MediaPlayer = MediaPlayer()
       //     mp.setDataSource("/storage/emulated/0/Android/data/com.example.fake_call/files/storage/emulated/0/kokos.mp3")
       //     Toast.makeText(this, path, Toast.LENGTH_SHORT).show()
            mp.setDataSource(path)
            mp.prepare()
            mp.start()

  //          Toast.makeText(this, "The recording playing", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun sendData(newDirect: String)
    {
        val sharedPreferences : SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor :SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply(){
            putString("STRING_KEY", newDirect)
        }.apply()
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show()
    }


    fun checkMicrophone(): Boolean
    {
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE))
        {
            return true
        }
        return false
    }
    fun givePermission()
    {
        val help : String = android.Manifest.permission.RECORD_AUDIO
        val help2 : String = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) ==
            PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(help, help2), MICROPHONE_PERMISSION_CODE)
        }
    }
    fun givePath(): String {
        val currentDateLocal = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {          //nastavenie aktualneho datumu
            Calendar.getInstance().time
        } else {
            "1.1.2001"
        }
        val cas = LocalDateTime.now()
        help.value = true
        val contextWrapper : ContextWrapper = ContextWrapper(applicationContext)
        val musicDirectory : File? = contextWrapper.getExternalFilesDir(Environment.getExternalStorageDirectory().toString())
       // val file : File? = File(musicDirectory, "/" + currentDateLocal + ".mp3")
        val file : File? = File(musicDirectory, "/" + nameOfRecord.value.toString() + ".mp3")
        if (file != null) {
            return file.path
        }
        return ""
    }

    fun builder()
    {
        val builder = AlertDialog.Builder(this)
        val inflater : LayoutInflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.edit_text_layout, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.et_editText)
        with(builder){
            setTitle("Enter name of new file")
            setPositiveButton("OK"){ dialog, which ->
                nameOfRecord.value = editText.text.toString()
                var path  = givePath()
                Toast.makeText(context,path,Toast.LENGTH_SHORT).show()
                startRecording(path)
                return@setPositiveButton
            }
            setNegativeButton("Cancel"){ dialog, which ->
                Log.d("Main", "Negative")
            }
            setView(dialogLayout)
            show()
        }
        help.value = false
    }
    fun startRecording(path : String)
    {
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
        //     mediaRecorder.setOutputFile("/storage/emulated/0/Android/data/com.example.fake_call/files/storage/emulated/0/kokos.mp3")
        mediaRecorder.setOutputFile(path)

        mediaRecorder.prepare()
        mediaRecorder.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        var inflater = menuInflater
        inflater.inflate(R.menu.menu_delete,menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.deleteAll)
        {
            Toast.makeText(applicationContext,"DELETE",Toast.LENGTH_SHORT).show()
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
    private fun refreshListView()
    {
        SwipeRefreshLayout.setOnRefreshListener {

        }
    }


}