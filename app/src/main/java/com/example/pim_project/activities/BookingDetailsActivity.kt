package com.example.pim_project.activities

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.pim_project.R
import com.example.pim_project.model.Booking
import com.example.pim_project.utils.ApiInterface
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingDetailsActivity : AppCompatActivity()
{
    private lateinit var toolbar: MaterialToolbar

    private lateinit var textDate: TextView
    private lateinit var textTime: TextView
    private lateinit var textTitle: TextView
    private lateinit var textReference: TextView
    private lateinit var textBrand: TextView
    private lateinit var textModel: TextView
    private lateinit var textLab: TextView
    private lateinit var textUserEmail: TextView
    private lateinit var textUserName: TextView
    private lateinit var textUserIdentifier: TextView
    private lateinit var textUserClassroom: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.booking_details)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }


        textDate = findViewById(R.id.booking_details_date)
        textTime = findViewById(R.id.booking_details_time)
        textTitle = findViewById(R.id.booking_details_title)
        textReference = findViewById(R.id.booking_details_refrence)
        textBrand = findViewById(R.id.booking_details_brand)
        textModel = findViewById(R.id.booking_details_model)
        textLab = findViewById(R.id.booking_details_lab)
        textUserEmail = findViewById(R.id.booking_details_user_email)
        textUserName = findViewById(R.id.booking_details_user_name)
        textUserIdentifier = findViewById(R.id.booking_details_user_id)
        textUserClassroom = findViewById(R.id.booking_details_user_classroom)


        getHardware()
    }

    private fun getHardware()
    {
        val progressDialog = ProgressDialog(this@BookingDetailsActivity)
        progressDialog.setMessage(getString(R.string.msg_loading))
        progressDialog.setCancelable(false)
        progressDialog.show()


        ApiInterface.create().getBooking(intent.getStringExtra("bookingId").toString()).enqueue(object :
            Callback<Booking> {
            override fun onResponse(call: Call<Booking>, response: Response<Booking>)
            {
                if (progressDialog.isShowing) progressDialog.dismiss()

                if(response.code() == 200)
                {
                    populateDate(response.body()!!)
                }

                if (response.code() == 404)
                {
                    Toast.makeText(this@BookingDetailsActivity, "Booking not found !", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Booking>, t: Throwable)
            {
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this@BookingDetailsActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun populateDate(booking: Booking)
    {
        textDate.text = booking.date
        textTime.text = booking.time
        textTitle.text = booking.hardware.title
        textReference.text = booking.hardware.reference
        textBrand.text = booking.hardware.brand
        textModel.text = booking.hardware.model
        textLab.text = booking.hardware.lab
        textUserEmail.text = booking.user.email
        textUserName.text = getString(R.string.user_full_name, booking.user.firstName, booking.user.lastName)
        textUserIdentifier.text = booking.user.espritIdentifier
        textUserClassroom.text = booking.user.classroom
    }

}