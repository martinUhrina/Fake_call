package com.example.fake_call

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CallAdapter(val callList:List<CallData>) : RecyclerView.Adapter<CallViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallViewHolder {
       var view =  LayoutInflater.from(parent.context).inflate(R.layout.call_row_recycle_view, parent,false)

        return CallViewHolder(view)
    }

    override fun onBindViewHolder(holder: CallViewHolder, position: Int) {
        val calldata = callList.get(position)
        holder.setCallData(calldata)
    }

    override fun getItemCount(): Int {
        return callList.size
    }
}