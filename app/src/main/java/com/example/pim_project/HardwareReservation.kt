package com.example.pim_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.appbar.MaterialToolbar

class HardwareReservation : AppCompatActivity()
{
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hardware_reservation)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.reservation)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }


    }
}