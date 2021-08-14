package com.example.fake_call

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.telecom.PhoneAccountHandle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.fake_call.databinding.FragmentTimerBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TimerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("DEPRECATION")
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentTimerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_timer, container, false)


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

            startTimer(longTimer = timer,binding,telNumber)
            //star call

                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), REQUEST_PHONE_CALL)
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

    private fun startIncomingCall() {

    }



    private fun startTimer(longTimer: Long, binding:FragmentTimerBinding, number:String) {
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
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), REQUEST_PHONE_CALL)
                    startCall(number)
                    goBack()
                }
                else
                {
                    startIncomingCall()
                    goBack()
                }
            }
        }
        cTimer.start()
    }

    private fun goBack() {
        findNavController(requireParentFragment()).navigate(R.id.action_timerFragment_to_welcomeFragment)
    }


    private fun startCall(number:String) {
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