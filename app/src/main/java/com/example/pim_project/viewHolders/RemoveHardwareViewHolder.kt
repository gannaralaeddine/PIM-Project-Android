package com.example.pim_project.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pim_project.R

class RemoveHardwareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    val title: TextView = itemView.findViewById(R.id.remove_hardware_title)
    val brand: TextView = itemView.findViewById(R.id.remove_hardware_brand)
    val model: TextView = itemView.findViewById(R.id.remove_hardware_model)
    val lab: TextView = itemView.findViewById(R.id.remove_hardware_lab)
    val removeIcon: ImageView = itemView.findViewById(R.id.hardware_remove)
}