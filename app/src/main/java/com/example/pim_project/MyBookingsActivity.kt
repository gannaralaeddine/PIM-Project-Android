package com.example.pim_project

import android.app.ProgressDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pim_project.adapters.MyBookingsAdapter
import com.example.pim_project.model.Booking
import com.example.pim_project.utils.ApiInterface
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyBookingsActivity : AppCompatActivity()
{
    private lateinit var toolbar: MaterialToolbar


    private lateinit var recyclerBookings: RecyclerView
    private lateinit var adapterBooking: MyBookingsAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var textNothing: TextView

    var bookingList: ArrayList<Booking> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bookings)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.my_booking_list)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        sharedPreferences = this.getSharedPreferences("prefs", MODE_PRIVATE)

        recyclerBookings = findViewById(R.id.recycler_my_bookings)
        textNothing = findViewById(R.id.text_my_booking_nothing)

        recyclerBookings.layoutManager = LinearLayoutManager(this@MyBookingsActivity, LinearLayoutManager.VERTICAL, false)

        getMyData()
    }


    private fun getMyData()
    {
        val progressDialog = ProgressDialog(this@MyBookingsActivity)
        progressDialog.setMessage(getString(R.string.msg_loading))
        progressDialog.setCancelable(false)
        progressDialog.show()

        ApiInterface.create().getMyBookingList(sharedPreferences.getString("userId", null).toString()).enqueue(object : Callback<List<Booking>?> {
            override fun onResponse(call: Call<List<Booking>?>, response: Response<List<Booking>?>)
            {
                if (progressDialog.isShowing) progressDialog.dismiss()

                bookingList.clear()
                if(response.body() != null)
                {
                    for (data in response.body()!!)
                    {
                        bookingList.add(data)
                    }
                    if (bookingList.size == 0)
                    {
                        textNothing.visibility = View.VISIBLE
                    }
                    else
                    {
                        adapterBooking = MyBookingsAdapter(this@MyBookingsActivity, bookingList)
                        recyclerBookings.adapter = adapterBooking
                    }
                }
            }

            override fun onFailure(call: Call<List<Booking>?>, t: Throwable)
            {
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this@MyBookingsActivity, t.message, Toast.LENGTH_SHORT).show()
                Log.e("error",t.message.toString())
            }
        })
    }
}