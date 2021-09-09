package com.example.omron.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.omron.R
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.OmronPeripheral

class ScannedDeviceAdapter(
    private var dataSet: ArrayList<OmronPeripheral>
) : RecyclerView.Adapter<ScannedDeviceAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val deviceTitle: TextView = view.findViewById(R.id.device_title)
        val deviceMac: TextView = view.findViewById(R.id.device_mac)
        val deviceRssi: TextView = view.findViewById(R.id.device_rssi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.scanned_device_list_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = dataSet[position]
        holder.deviceTitle.text = current.modelName
        holder.deviceMac.text = current.uuid
        holder.deviceRssi.text = current.rssi.toString()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun add(new: ArrayList<OmronPeripheral>) {
        dataSet = new
        notifyDataSetChanged()
    }
}