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
import com.example.pim_project.adapters.RemoveHardwareAdapter
import com.example.pim_project.model.Booking
import com.example.pim_project.model.Hardware
import com.example.pim_project.utils.ApiInterface
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllHardwaresActivity : AppCompatActivity()
{

    private lateinit var toolbar: MaterialToolbar

    private lateinit var recyclerManageHardwares: RecyclerView
    private lateinit var adapterRemoveHardware: RemoveHardwareAdapter
    private lateinit var textNothing: TextView

    var harwaresList: ArrayList<Hardware> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_hardwares)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.my_booking_list)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        textNothing = findViewById(R.id.text_all_hardwares_nothing)

        recyclerManageHardwares = findViewById(R.id.recycler_manage_hardwares)

        recyclerManageHardwares.layoutManager = LinearLayoutManager(this@AllHardwaresActivity, LinearLayoutManager.VERTICAL, false)

        getMyData()

    }

    private fun getMyData()
    {
        val progressDialog = ProgressDialog(this@AllHardwaresActivity)
        progressDialog.setMessage(getString(R.string.msg_loading))
        progressDialog.setCancelable(false)
        progressDialog.show()

        ApiInterface.create().getHardwareList().enqueue(object :
            Callback<List<Hardware>?> {
            override fun onResponse(call: Call<List<Hardware>?>, response: Response<List<Hardware>?>)
            {
                if (progressDialog.isShowing) progressDialog.dismiss()

                harwaresList.clear()
                if(response.body() != null)
                {
                    for (data in response.body()!!)
                    {
                        harwaresList.add(data)
                    }

                    if (harwaresList.size == 0)
                    {
                        textNothing.visibility = View.VISIBLE
                    }
                    else
                    {
                        adapterRemoveHardware = RemoveHardwareAdapter(this@AllHardwaresActivity, harwaresList)
                        recyclerManageHardwares.adapter = adapterRemoveHardware
                    }
                }
            }

            override fun onFailure(call: Call<List<Hardware>?>, t: Throwable)
            {
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this@AllHardwaresActivity, t.message, Toast.LENGTH_SHORT).show()
                Log.e("error",t.message.toString())
            }
        })
    }
}