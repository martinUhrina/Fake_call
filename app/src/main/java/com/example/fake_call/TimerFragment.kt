package com.example.fake_call


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.fake_call.database.CallDatabase
import com.example.fake_call.databinding.FragmentTimerBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.NullPointerException


private val Any.placeCall: Unit
    get() {}

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TimerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class TimerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)


        }

    }



    val REQUEST_PHONE_CALL = 1
    var POMOC = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        val binding: FragmentTimerBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_timer,
            container,
            false
        )


        var name:String
        var telNumber:String
        arguments.let {
            name = TimerFragmentArgs.fromBundle(it!!).name
            telNumber = TimerFragmentArgs.fromBundle(it!!).number
        }

        binding.btnTimerFragment.setOnClickListener {
            //timer
            val function = binding.idFunction.checkedRadioButtonId
            var timerID = binding.idChipGroup.checkedChipId
            var timer: Long = 1
            when(timerID){
                binding.idteraz.id -> timer = 0
                binding.id10.id -> timer = 10000
                binding.id20.id -> timer = 20000
            }

            startTimer(longTimer = timer, binding, telNumber, name)
            //star call

            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.MANAGE_OWN_CALLS),
                REQUEST_PHONE_CALL
            )
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.CALL_PHONE),
                        REQUEST_PHONE_CALL
                    )
                }
                else {
             /*       if(function == binding.idCalling.id) {

                    startCall(telNumber)
                    }
                    else{
                        startIncomingCall()
                    }
                    goBack()
              */}

        }
        return binding.root
    }

  /*  @SuppressLint("NewApi")
    fun startIncomingCall() {

        @RequiresApi(Build.VERSION_CODES.M)
        class MyConnectionService : ConnectionService(){
        }                                                                   //!!!!!!!!!!!!!!!!!!!!!!!!

        var phoneAcount : PhoneAccountHandle = (PhoneAccountHandle(ComponentName.unflattenFromString("jani")!!,"11"))
        var zvazok : Bundle = requireArguments()

        var variable : TelecomManager =
        variable.addNewIncomingCall(phoneAcount,zvazok)

    }
*/
    private fun startIncomingCall(telNumber: String, name: String) {


      var thiscontext = this.context

      if (thiscontext != null) {
          FakeRing(telNumber, thiscontext, name)
      }




 //       val customView = RemoteViews(android.widget.FrameLayout.AUTOFILL_HINT_PHONE, R.layout.fragment_timer)
     //   var notificationIntent = Intent(this, CallingActivity::class.java)
  //      var hungupIntent = Intent(this, HungUpBroadcast::class.java)
    //    var answerIntent = Intent(this,AnwerCallActivity::class.java)

        //startActivity(notificationIntent as Intent?)

  //      val pendingIntent = PendingIntent.getActivity(requireContext(),0, notificationIntent as Intent?,
    //    PendingIntent.FLAG_UPDATE_CURRENT)
     //   val hungupPendingIntent = PendingIntent.getBroadcast(requireContext(),0,
       //         hungupIntent as Intent?,PendingIntent.FLAG_UPDATE_CURRENT)
    //    val answerPendingIntent = PendingIntent.getActivity(requireContext(),0,
      //          answerIntent as Intent?,PendingIntent.FLAG_UPDATE_CURRENT)

//        customView.setOnClickPendingIntent(R.id.btnAnwer,answerPendingIntent)
  //      customView.setOnClickPendingIntent(R.id.btnDeclime,hungupPendingIntent)

//        notificationIntent.setData(Uri.parse("tel: 456464848"))
    //    startActivity(notificationIntent)

       /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        }
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel: $number")
            startActivity(callIntent)*/
    }
/*
    @JvmName("Intent2")
    private fun Intent(timerFragment: TimerFragment, java: Class<AnwerCallActivity>): Any {
        return Intent()
    }

    @JvmName("Intent1")
    private fun Intent(timerFragment: TimerFragment, java: Class<HungUpBroadcast>): Any
    {
        return Intent()
    }

    private fun Intent(timerFragment: TimerFragment, java: Class<CallingActivity>): Any
    {
        return Intent()
    }

*/
fun FakeRing(fakeNumber: String, context: Context, name: String) {

    val FakeRing = Intent(context, FakeCallRinging::class.java)
    FakeRing.putExtra("fakeNumber", fakeNumber)
    FakeRing.putExtra("fakeName", name)
    startActivity(FakeRing)
}


    private fun startTimer(
        longTimer: Long,
        binding: FragmentTimerBinding,
        number: String,
        name: String
    ) {                                  //spustenie casovaca
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            binding.idCountDown.setTransitionVisibility(View.VISIBLE)
        }

        var cTimer = object  : CountDownTimer(longTimer, 1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.idCountDown.text = (millisUntilFinished/1000).toString()
            }

            val function = binding.idFunction.checkedRadioButtonId
            override fun onFinish() {
                if (function == binding.idCalling.id) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.CALL_PHONE),
                        REQUEST_PHONE_CALL
                    )
                    startCall(number)
                    goBack()
                }
                else
                {
                   // kurnik()
                    addToDatabase(name,number)
                    startIncomingCall(number, name)
                    goBack()
                }
            }
        }
        cTimer.start()
    }

    /*private fun kurnik()
    {
        var data = CallData(3,"Fero","98467135")
        val dao = context?.let { CallDatabase.getDatabase(it.applicationContext).dao() }
        GlobalScope.launch {
            dao?.insertData(data)
        }

    }*/

    private  fun addToDatabase(name:String,number: String) {
        var data = CallData(meno = name, cislo = number)

        val dao = context?.let { CallDatabase.getDatabase(it.applicationContext).dao() }
        GlobalScope.launch {
            dao?.insertData(data)
        }
    }


    private fun goBack() {                                                                                                  //po hovore ist na uvodnu obrazovku
        findNavController(requireParentFragment()).navigate(R.id.action_timerFragment_to_welcomeFragment)
    }


    private fun startCall(number: String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel: $number")
        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    //    if(requestCode == REQUEST_PHONE_CALL) startCall()
    }




    fun newInstance(param1: String, param2: String) =
            WelcomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }


   companion object {

       /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuestionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TimerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }

}
