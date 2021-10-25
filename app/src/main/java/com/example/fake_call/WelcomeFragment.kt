package com.example.fake_call

import android.content.Context
import android.icu.text.CaseMap
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.fake_call.databinding.FragmentWelcomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WelcomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment() {
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentWelcomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)
        setHasOptionsMenu(true)
        binding.btnNext.setOnClickListener {view:View ->
            if(binding.EditTextName.text.toString() == "" || binding.editTextNumber.text.toString() == "") {
                Toast.makeText(context, "Prosim, zadajte vsetky parametre", Toast.LENGTH_SHORT).show()
            }
            else
            {
                view.findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToTimerFragment(binding.EditTextName.text.toString(), binding.editTextNumber.text.toString()))
            }
        }

       return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_welcome,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = this.findNavController()
        return NavigationUI.onNavDestinationSelected(item,navController)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WelcomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WelcomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}