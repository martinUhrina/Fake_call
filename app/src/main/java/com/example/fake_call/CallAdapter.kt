package com.example.fake_call

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CallAdapter(var callList:List<CallData>) : RecyclerView.Adapter<CallViewHolder>() {

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

    fun clearItems()
    {
    Log.i("!!!!!!!!!!!!!!!!!!!!!!!!!","sme v clearItems")
        callList = emptyList()
        notifyDataSetChanged()
    }
}