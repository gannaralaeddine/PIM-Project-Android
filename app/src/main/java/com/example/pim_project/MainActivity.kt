package com.example.pim_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.pim_project.fragments.AddHardwareFragment
import com.example.pim_project.fragments.HardwareFragment
import com.example.pim_project.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener
{
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().replace(R.id.frame_view, HardwareFragment() ).commit()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_home -> {
                val fragment = HardwareFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_view, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.ic_profile -> {
                val fragment = ProfileFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_view, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.ic_add_hardware -> {
                val fragment = AddHardwareFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_view, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
        }
        return false
    }

}