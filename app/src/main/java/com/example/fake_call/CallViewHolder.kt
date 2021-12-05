package com.example.fake_call


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class CallViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private var callName: TextView? = null
    private var callNumber: TextView? = null
    private var callImage : ImageView? = null
    private var callData : TextView? = null


    init {
        callName = view.findViewById(R.id.callName)
        callNumber = view.findViewById(R.id.callNumber)
        callImage = view.findViewById(R.id.imageViewInRecycle)
        callData = view.findViewById(R.id.callDate)
    }

    fun setCallData(calldata: CallData) {
        callName?.text = calldata.meno
        callNumber?.text = calldata.cislo
        callImage?.setImageResource(R.drawable.acceptcall)
        callData?.text = calldata.currentDate
    }
}
