package com.example.fake_call


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.util.Log
import android.util.Log.INFO
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.fake_call.database.CallDatabase
import com.example.fake_call.databinding.FragmentTimerBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate


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
    var counterRemainingTime = MutableLiveData<Long>()
    var unitToEnd = MutableLiveData<Long>()
    var nameLive = MutableLiveData<String>()
    var numberLive = MutableLiveData<String>()
    var isOnStop = MutableLiveData<Boolean>()
    var countQuantity = MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            counterRemainingTime.value = 0
            unitToEnd.value =10
            isOnStop.value = true
            countQuantity.value = 0
        }
    }

    val REQUEST_PHONE_CALL = 1
    var POMOC = false
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.i("TimerFragment","sme v onCreteView")
        val binding: FragmentTimerBinding = DataBindingUtil.inflate(
                inflater,R.layout.fragment_timer,container,false
        )


        var name:String
        var telNumber:String
        arguments.let {
            name = TimerFragmentArgs.fromBundle(it!!).name
            telNumber = TimerFragmentArgs.fromBundle(it!!).number
        }
        nameLive.value = name
        numberLive.value = telNumber

        binding.btnTimerFragment.setOnClickListener {
            //timer
            if (!POMOC)
            {
                counterRemainingTime.value = 0
                val function = binding.idFunction.checkedRadioButtonId
                var timerID = binding.idChipGroup.checkedChipId
                var timer: Long = 10
                when(timerID){
                    binding.idteraz.id -> timer = 10
                    binding.id10.id -> timer = 10000
                    binding.id20.id -> timer = 20000
                }
                when(timerID)
                {
                    binding.idteraz.id -> counterRemainingTime.value = 10
                    binding.id10.id -> counterRemainingTime.value = 10000
                    binding.id20.id -> counterRemainingTime.value = 20000
                }

                POMOC = true
            //    startTimer(longTimer = timer, binding, telNumber, name)
                startTimer(longTimer = counterRemainingTime.value!!, binding, telNumber, name)
            }

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


    private fun startIncomingCall(telNumber: String, name: String) {

      var thiscontext = this.context

      if (thiscontext != null) {
          FakeRing(telNumber, thiscontext, name)
      }
    }

fun FakeRing(fakeNumber: String, context: Context, name: String) {

    val FakeRing = Intent(context, FakeCallRinging::class.java)
    FakeRing.putExtra("fakeNumber", fakeNumber)
    FakeRing.putExtra("fakeName", name)
    startActivity(FakeRing)
}


    private fun startTimer(                                                                         //zaciatok casovaca
        longTimer: Long,
        binding: FragmentTimerBinding,
        number: String,
        name: String
    ) {


        //spustenie casovaca
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            binding.idCountDown.setTransitionVisibility(View.VISIBLE)
        }

        var cTimer = object  : CountDownTimer(longTimer, 1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.idCountDown.text = (millisUntilFinished/1000).toString()
                unitToEnd.value = millisUntilFinished
            }

            val function = binding.idFunction.checkedRadioButtonId
            override fun onFinish() {
                if (isOnStop.value == true && countQuantity.value == 0) {

                    if (function == binding.idCalling.id) {
                        ActivityCompat.requestPermissions(
                                requireActivity(),
                                arrayOf(Manifest.permission.CALL_PHONE),
                                REQUEST_PHONE_CALL
                        )
                        addToDatabase(name, number)
                        startCall(number)
                        goBack()
                    } else {
                        Log.i("TimerFragment", "pustam hovor cez Klasiku " + isOnStop.value)
                        countQuantity.value = 1
                        addToDatabase(name, number)
                        startIncomingCall(number, name)
                        goBack()
                    }
                }
            }
        }
        cTimer.start()
    }


    private fun startTimerOnStop()
    {
        var cTimer = object  : CountDownTimer(unitToEnd.value!!.toLong(), 1000)
        {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                if (isOnStop.value == false && countQuantity.value == 0) {
                    Log.i("TimerFragment", "pustam hovor cez onStop " + isOnStop.value)
                    countQuantity.value = 1
                    startIncomingCall(numberLive.value.toString(), nameLive.value.toString())
                }
            }
        }
        cTimer.start()
    }


    private  fun addToDatabase(name:String, number: String) {

        val currentDateLocal = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {          //nastavenie aktualneho datumu
            LocalDate.now()
        } else {
            "1.1.2001"
        }

        var data = CallData(meno = name, cislo = number, currentDate = currentDateLocal.toString())                 //poslanie dat do enetity
   //     val dao = CallDatabase.getDatabase(this.requireContext()).dao()
        val dao = context?.let { CallDatabase.getDatabase(it.applicationContext).dao() }                            //vytvorenie vedlajsieho vlakna
        GlobalScope.launch {
            dao?.insertData(data)                                                                                    //poslanie cez DAO do databasy
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

    }


    override fun onStop() {
   //     Log.i("TimerFragment", "sme v onStop" + unitToEnd.value)
        isOnStop.value = false
 //       startTimerOnStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        Log.i("TimerFragment", "sme v onStart")
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Make Call"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i("TimerFragment", "sme v onAttach")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("TimerFragment", "sme v onActivityCreated")
    }

    override fun onResume() {
        Log.i("TimerFragment", "sme v onResume")
        if(isOnStop.value == false) {
            Log.i("TimerFragment", "pustam casovac cez onResume")
            startTimerOnStop()
        }
         super.onResume()
    }

    override fun onPause() {
        Log.i("TimerFragment", "sme v onPause" + unitToEnd.value)
        super.onPause()
    }



    override fun onDestroy() {
        Log.i("TimerFragment", "sme v onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.i("TimerFragment", "sme v onDetach")
        super.onDetach()
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

