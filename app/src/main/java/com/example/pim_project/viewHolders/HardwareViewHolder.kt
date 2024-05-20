package com.example.pim_project.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pim_project.R

class HardwareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    val title: TextView = itemView.findViewById(R.id.hardware_title)
    val brand: TextView = itemView.findViewById(R.id.hardware_brand)
    val model: TextView = itemView.findViewById(R.id.hardware_model)
    val lab: TextView = itemView.findViewById(R.id.hardware_lab)
}