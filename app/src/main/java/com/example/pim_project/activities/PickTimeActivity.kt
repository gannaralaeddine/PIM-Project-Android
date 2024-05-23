package com.example.pim_project.activities

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.pim_project.R
import com.example.pim_project.model.DispoDate
import com.example.pim_project.model.Hardware
import com.example.pim_project.model.ResponseUser
import com.example.pim_project.utils.ApiInterface
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickTimeActivity : AppCompatActivity()
{
    private lateinit var toolbar: MaterialToolbar

    private var timeList: ArrayList<String> = ArrayList()
    private lateinit var timePicker: Spinner
    private lateinit var date: String
    private lateinit var userId: String
    private lateinit var hardwareId: String

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_time)

        createTimeList()

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.reservation)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)

        userId = sharedPreferences.getString("userId", null).toString()
        hardwareId = intent.getStringExtra("hardwareId").toString()

        retreiveHardware(hardwareId)


        timePicker = findViewById(R.id.reservation_time_picker)

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, timeList
        )
        timePicker.adapter = adapter

        val intent = intent
        date = intent.getStringExtra("date")!!


        findViewById<Button>(R.id.btn_confirm_reservation).setOnClickListener {
            showDialog()
        }
    }

    private fun retreiveHardware(hardwareId: String)
    {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage(getString(R.string.msg_loading))
        progressDialog.setCancelable(false)
        progressDialog.show()



        ApiInterface.create().getHardware(hardwareId).enqueue(object : Callback<Hardware> {
            override fun onResponse(call: Call<Hardware>, response: Response<Hardware>)
            {
                if (progressDialog.isShowing) progressDialog.dismiss()

                if(response.body() != null)
                {
                    getAvailableTimes(response.body()!!.dispoDates)
                }
            }

            override fun onFailure(call: Call<Hardware>, t: Throwable)
            {
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this@PickTimeActivity, t.message, Toast.LENGTH_SHORT).show()
                e("error",t.message.toString())
            }
        })

    }

    private fun confirmBooking()
    {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage(getString(R.string.msg_loading))
        progressDialog.setCancelable(false)
        progressDialog.show()

        ApiInterface.create().hardwareBooking(userId, hardwareId, date, timePicker.selectedItem.toString())
            .enqueue(object : Callback<ResponseUser> {
                override fun onResponse(call: Call<ResponseUser>, res: Response<ResponseUser>)
                {
                    if (progressDialog.isShowing) progressDialog.dismiss()

                    if(res.code() == 201)
                    {
                        Toast.makeText(this@PickTimeActivity, getString(R.string.msg_reservation_confirmed), Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@PickTimeActivity, MainActivity::class.java))
                    }
                    else
                    {
                        Toast.makeText(this@PickTimeActivity, "Error !", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseUser>, t: Throwable)
                {
                    if (progressDialog.isShowing) progressDialog.dismiss()
                    Toast.makeText(this@PickTimeActivity, t.message, Toast.LENGTH_SHORT).show()
                    e("error",t.message.toString())
                }
            })
    }


    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.confirmation))
        builder.setMessage(getString(R.string.are_you_sure_you_want_to_proceed))

        builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->

            confirmBooking()
            dialog.dismiss()
        }

        builder.setNegativeButton(getString(R.string.no)) { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }


    private fun createTimeList()
    {
        timeList.clear()

        for(i in 9..18)
        {
            timeList.add( "$i:00-${i + 1}:00" )
        }
    }

    private fun getAvailableTimes(dispoDates: List<DispoDate>)
    {
        for (item in dispoDates)
        {
            e("item",item.toString())
            e("item.date",item.date)
            e("date",date)

            if (item.date == date)
            {
                e("dispoTimes",item.dispoTimes.toString())
                for (itemTime in item.dispoTimes)
                {
                    if (timeList.contains(itemTime))
                    {
                        timeList.remove(itemTime)
                    }
                }
                return
            }
        }


    }
}