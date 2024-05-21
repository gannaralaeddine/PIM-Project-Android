package com.example.pim_project.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pim_project.R
import com.example.pim_project.adapters.HardwareAdapter
import com.example.pim_project.model.Hardware
import com.example.pim_project.utils.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HardwareFragment : Fragment()
{

    private lateinit var recyclerHardware: RecyclerView
    private lateinit var adapterHardware: HardwareAdapter

    var hardwareList: ArrayList<Hardware> = ArrayList()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle? ): View
    {
        val view: View = inflater.inflate(R.layout.fragment_hardware, container, false)

        recyclerHardware = view.findViewById(R.id.recycler_hardware)

        recyclerHardware.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        getMyData()

        return view
    }


    private fun getMyData()
    {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage(getString(R.string.msg_loading))
        progressDialog.setCancelable(false)
        progressDialog.show()

        ApiInterface.create().getHardwareList().enqueue(object : Callback<List<Hardware>?> {
            override fun onResponse(call: Call<List<Hardware>?>, response: Response<List<Hardware>?>)
            {
                if (progressDialog.isShowing) progressDialog.dismiss()

                hardwareList.clear()
                if(response.body() != null)
                {
                    for (data in response.body()!!)
                    {
                        hardwareList.add(data)
                    }
                    adapterHardware = HardwareAdapter(requireActivity(), hardwareList)
                    recyclerHardware.adapter = adapterHardware
                }
            }

            override fun onFailure(call: Call<List<Hardware>?>, t: Throwable)
            {
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                Log.e("error",t.message.toString())
            }
        })
    }
}