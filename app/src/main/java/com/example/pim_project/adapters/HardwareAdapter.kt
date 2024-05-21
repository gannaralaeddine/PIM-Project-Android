package com.example.pim_project.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pim_project.HardwareReservation
import com.example.pim_project.R
import com.example.pim_project.model.Hardware
import com.example.pim_project.viewHolders.HardwareViewHolder

class HardwareAdapter(val context: FragmentActivity?, private var hardwareList: ArrayList<Hardware>) : RecyclerView.Adapter<HardwareViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HardwareViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hardware, parent, false)

        return HardwareViewHolder(view)
    }

    override fun onBindViewHolder(holder: HardwareViewHolder, position: Int)

    {
        val hardware = hardwareList[position]

        holder.title.text = hardware.title
        holder.brand.text = hardware.brand
        holder.model.text = hardware.model
        holder.lab.text = hardware.lab

        holder.itemView.setOnClickListener{

            val intent = Intent(holder.itemView.context, HardwareReservation::class.java)
            intent.apply {
                putExtra("hardwareId", hardware.id)
                putExtra("title", hardware.title)
                putExtra("organizer", hardware.brand)
                putExtra("program", hardware.model)
                putExtra("hikingImage", hardware.lab)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = hardwareList.size

}