package com.example.pim_project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pim_project.R
import com.example.pim_project.model.Hardware
import com.example.pim_project.viewHolders.RemoveHardwareViewHolder

class RemoveHardwareAdapter(val context: FragmentActivity?, private var hardwareList: ArrayList<Hardware>) : RecyclerView.Adapter<RemoveHardwareViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoveHardwareViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hardware_delete, parent, false)

        return RemoveHardwareViewHolder(view)
    }

    override fun onBindViewHolder(holder: RemoveHardwareViewHolder, position: Int)

    {
        val hardware = hardwareList[position]

        holder.title.text = hardware.title
        holder.brand.text = hardware.brand
        holder.model.text = hardware.model
        holder.lab.text = hardware.lab

        holder.removeIcon.setOnClickListener {
            Toast.makeText(context, "Remove click !!", Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int = hardwareList.size

}