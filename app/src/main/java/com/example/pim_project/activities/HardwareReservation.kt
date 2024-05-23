package com.example.pim_project.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import com.example.pim_project.R
import com.google.android.material.appbar.MaterialToolbar
import java.util.Calendar

class HardwareReservation : AppCompatActivity()
{
    private lateinit var toolbar: MaterialToolbar

    private lateinit var datePicker: DatePicker

    private lateinit var hardwareId: String

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hardware_reservation)

        sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        hardwareId = intent.getStringExtra("hardwareId").toString()


        toolbar = findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.reservation)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        datePicker = findViewById(R.id.reservation_date_picker)
        datePicker.minDate = Calendar.getInstance().timeInMillis+24*60*60*1000 //set min date to tomorrow

        findViewById<Button>(R.id.btn_reservation_next).setOnClickListener {
            val intent = Intent(this, PickTimeActivity::class.java)
            intent.putExtra("date", "${ datePicker.dayOfMonth }/${ datePicker.month + 1 }/${ datePicker.year }")
            intent.putExtra("hardwareId", hardwareId)
            startActivity(intent)
        }
    }


}