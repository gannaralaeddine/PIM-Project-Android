package com.example.pim_project.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pim_project.R
import com.example.pim_project.activities.BookingDetailsActivity
import com.example.pim_project.model.Booking
import com.example.pim_project.viewHolders.BookingViewHolder

class MyBookingsAdapter(val context: FragmentActivity?, private var bookingList: ArrayList<Booking>) : RecyclerView.Adapter<BookingViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_my_hardware, parent, false)

        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int)
    {
        val booking = bookingList[position]

        holder.title.text = booking.hardware.title
        holder.brand.text = booking.hardware.brand
        holder.model.text = booking.hardware.model
        holder.lab.text = booking.hardware.lab
        holder.date.text = booking.date
        holder.time.text = booking.time

        holder.itemView.setOnClickListener {
            val intent = Intent(context, BookingDetailsActivity::class.java)
            intent.putExtra("bookingId", booking.id)
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = bookingList.size

}