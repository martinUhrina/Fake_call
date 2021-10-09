package com.example.fake_call

import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class FakeCallRinging : AppCompatActivity() {
    private var networkCarrier: String? = null
    private var ringTone: MediaPlayer? = null
    var titleBar: TextView? = null
    var fakeNumber: TextView? = null
    var fakeName: TextView? = null
    var answerCall: Button? = null
    var rejectCall: Button? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fakeringlayout)
        InitialiseControl()
        GetNetworkOperatorName()
        AssignFakeNumberAndDisplay()
        StartRingTone()
        answerCall!!.setOnClickListener { ringTone!!.stop() }
        rejectCall!!.setOnClickListener {
            ringTone!!.stop()
            val homeIntent = Intent(Intent.ACTION_MAIN)
            homeIntent.addCategory(Intent.CATEGORY_HOME)
            homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(homeIntent)
        }
    }

    private fun InitialiseControl() {
        fakeNumber = findViewById(R.id.chosenfakenumber)
        fakeName = findViewById(R.id.chosenfakename)
        answerCall = findViewById(R.id.answercall)
        rejectCall = findViewById(R.id.rejectcall)
    }

    private fun StartRingTone() {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        ringTone = MediaPlayer.create(applicationContext, notification)
        ringTone = MediaPlayer.create(this, R.raw.ringtonemusic)
        ringTone!!.start()
    }

    private fun AssignFakeNumberAndDisplay() {
        val number = contactNumber
        fakeNumber!!.text = number
        val name = contactName
        fakeName!!.text = name
    }

    private fun GetNetworkOperatorName() {
        val tm = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        networkCarrier = tm.networkOperatorName
        titleBar = findViewById(R.id.textView1)
        if (networkCarrier != null) {
            titleBar!!.text = "$networkCarrier"
        } else {
            titleBar!!.text = "Incoming call"
        }
    }

    private val contactNumber: String?
        private get() {
            var contact: String? = null
            val myIntent: Intent = getIntent()
            val mIntent = myIntent.extras
            if (mIntent != null) {
                contact = mIntent.getString("fakeNumber")
            }
            return contact
        }
    private val contactName:String?
    private get() {
        var contact:String? = null
        val myIntent: Intent = getIntent()
        val mIntent = myIntent.extras
        if(mIntent != null){
            contact = mIntent.getString("fakeName")
        }
        return contact
    }
}