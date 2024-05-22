package com.example.pim_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.google.android.material.appbar.MaterialToolbar

class AdminActivity : AppCompatActivity()
{
    private lateinit var toolbar: MaterialToolbar


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.welcome_admin)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }


        findViewById<CardView>(R.id.manage_all_hardwares).setOnClickListener {
            startActivity(Intent(this, AllHardwaresActivity::class.java))
        }

        findViewById<CardView>(R.id.manage_hardwares).setOnClickListener {
            startActivity(Intent(this, AddHardwareActivity::class.java))
        }

    }
}