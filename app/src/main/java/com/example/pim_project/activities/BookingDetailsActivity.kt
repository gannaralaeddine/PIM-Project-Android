package com.example.pim_project.activities

import android.app.ProgressDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.pim_project.R
import com.example.pim_project.model.Booking
import com.example.pim_project.model.ResponseUser
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
    private lateinit var btnCancelBooking: Button

    private lateinit var sharedPreferences: SharedPreferences

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
        btnCancelBooking =findViewById<Button>(R.id.btn_cancel_booking)

        sharedPreferences = this.getSharedPreferences("prefs", MODE_PRIVATE)

        btnCancelBooking.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.confirmation))
            builder.setMessage(getString(R.string.are_you_sure_you_want_to_cancel_booking))

            builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->

                cancelBooking()
                dialog.dismiss()
            }

            builder.setNegativeButton(getString(R.string.no)) { dialog, which ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()

        }

        if (sharedPreferences.getString("userRole", null).toString() == "user")
        {
            btnCancelBooking.visibility = View.VISIBLE
        }
        else
        {
            btnCancelBooking.visibility = View.GONE
        }

        getHardware()
    }

    private fun cancelBooking()
    {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage(getString(R.string.msg_loading))
        progressDialog.setCancelable(false)
        progressDialog.show()

        ApiInterface.create().cancelBooking(intent.getStringExtra("bookingId").toString()).enqueue(object: Callback<ResponseUser> {

            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (progressDialog.isShowing) progressDialog.dismiss()

                if (response.code() == 200)
                {
                    Toast.makeText(this@BookingDetailsActivity, getString(R.string.Booking_canceled), Toast.LENGTH_SHORT).show()
                    return
                }

                if (response.code() == 404)
                {
                    Toast.makeText(this@BookingDetailsActivity,getString(R.string.msg_error_getting_user_data), Toast.LENGTH_SHORT).show()
                    return
                }

            }
            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this@BookingDetailsActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
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