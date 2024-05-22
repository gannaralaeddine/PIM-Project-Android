package com.example.pim_project.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pim_project.R

class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    val title: TextView = itemView.findViewById(R.id.my_hardware_title)
    val brand: TextView = itemView.findViewById(R.id.my_hardware_brand)
    val model: TextView = itemView.findViewById(R.id.my_hardware_model)
    val lab: TextView = itemView.findViewById(R.id.my_hardware_lab)
    val date: TextView = itemView.findViewById(R.id.my_hardware_date)
    val time: TextView = itemView.findViewById(R.id.my_hardware_time)
}