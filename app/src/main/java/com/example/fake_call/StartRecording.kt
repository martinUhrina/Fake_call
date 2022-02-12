package com.example.fake_call

import android.content.*
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
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
import java.util.*
import kotlin.collections.ArrayList


class StartRecording : AppCompatActivity() {
    var MICROPHONE_PERMISSION_CODE = 200;
    var nameOfRecord = MutableLiveData<String>()
    var actualChosse = MutableLiveData<String>()
    var help = MutableLiveData<Boolean>()
    var mediaRecorder : MediaRecorder = MediaRecorder()
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_recording)

        var recording : Button = findViewById(R.id.recoding)
        var stop : Button = findViewById(R.id.stop)
        var play : Button = findViewById(R.id.play)


        val actionbar = supportActionBar
        actionbar!!.title = "Records"
        actionbar.setDisplayHomeAsUpEnabled(true)

        stop.isEnabled = false
        var path = String()

        if (checkMicrophone())
        {
            givePermission()
        }
        var listView : ListView = findViewById(R.id.ListView)

        var mp3file : ArrayList<String> = ArrayList()

  //      var skuska : File = File("/storage/emulated/0/Android/data/com.example.fake_call/files/storage/emulated/0/Tue Jan 18 21:34:07 GMT+01:00 2022.mp3")

        var skuska3 : ArrayList<File> = ArrayList()

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
            this.actualChosse.value = adapter.getItem(position).toString()
              Toast.makeText(this,"Chosse: " + adapter.getItem(position).toString(), Toast.LENGTH_SHORT).show()
       //     this.actualRecord.value = givePath().toString()
            sendData(adapter.getItem(position).toString())

        }

        SwipeRefreshLayout.setOnRefreshListener {
            var mp3file : ArrayList<String> = ArrayList()
            var skuska3 : ArrayList<File> = ArrayList()
            var listView : ListView = findViewById(R.id.ListView)


            File("/storage/emulated/0/Android/data/com.example.fake_call/files/storage/emulated/0/").walkBottomUp().forEach {
                skuska3.add(it)
            }

            for (f in skuska3)
            {
                mp3file.add(f.name)
            }
            var adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, mp3file)
            listView.adapter = adapter;

        }

        recording.setOnClickListener {
            builder()
            stop.isEnabled = true

            //      Toast.makeText(this, path, Toast.LENGTH_SHORT).show()
         /*   mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
       //     mediaRecorder.setOutputFile("/storage/emulated/0/Android/data/com.example.fake_call/files/storage/emulated/0/kokos.mp3")
            mediaRecorder.setOutputFile(path)

            mediaRecorder.prepare()
            mediaRecorder.start()
*/
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
            val musicDirectory : File? = applicationContext.getExternalFilesDir(Environment.getExternalStorageDirectory().toString())
       //     mp.setDataSource("/storage/emulated/0/Android/data/com.example.fake_call/files/storage/emulated/0/kokos.mp3")
   //         Toast.makeText(this, musicDirectory.toString() +"/" + actualChosse.value.toString(), Toast.LENGTH_SHORT).show()
            var pathToFile : String = musicDirectory.toString() +"/" + actualChosse.value.toString()
            mp.setDataSource(pathToFile)
            mp.prepare()
            mp.start()
            Toast.makeText(this, "The recording playing", Toast.LENGTH_SHORT).show()
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
 //       Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show()
        Log.i("StartRecording","DATA SAVED")
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
        help.value = true
        val contextWrapper : ContextWrapper = ContextWrapper(applicationContext)
        val musicDirectory : File? = contextWrapper.getExternalFilesDir(Environment.getExternalStorageDirectory().toString())
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
                Toast.makeText(context,"Recording started",Toast.LENGTH_SHORT).show()
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
            deleteThis()
        }
        else {
            goBack()
        }
        return true
    }

    private fun deleteThis() {
        var deletedFile : String = String()
        val builder = AlertDialog.Builder(this)
        val inflater : LayoutInflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.edit_text_layout, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.et_editText)
        with(builder)
        {
            setTitle("Insert deleted file")
            setPositiveButton("DELETE") { dialog, which ->
                deletedFile = editText.text.toString()
                var file = File("/storage/emulated/0/Android/data/com.example.fake_call/files/storage/emulated/0/"+ deletedFile + ".mp3")
                if(file.exists())
                {
                    Toast.makeText(context,"This file not exist", Toast.LENGTH_SHORT).show()
                }
                file.delete()
            }
            setNegativeButton("Cancel"){ dialog, which ->
                Log.d("Main", "Negative")
            }
            setView(dialogLayout)
            show()
        }
    }

    private fun goBack() {
        val mainActivityLaunch = Intent(this, MainActivity::class.java)
        startActivity(mainActivityLaunch)
    }
    private fun refreshListView()
    {
        SwipeRefreshLayout.setOnRefreshListener {
            var mp3file : ArrayList<String> = ArrayList()
            var skuska3 : ArrayList<File> = ArrayList()
            var listView : ListView = findViewById(R.id.ListView)


            File("/storage/emulated/0/Android/data/com.example.fake_call/files/storage/emulated/0/").walkBottomUp().forEach {
                skuska3.add(it)
            }

            for (f in skuska3)
            {
                mp3file.add(f.name)
            }
            var adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, mp3file)
            listView.adapter = adapter;

        }
    }


}