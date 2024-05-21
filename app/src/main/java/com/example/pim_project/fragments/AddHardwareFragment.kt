package com.example.pim_project.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.pim_project.R
import com.example.pim_project.model.ResponseUser
import com.example.pim_project.utils.ApiInterface
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddHardwareFragment : Fragment()
{
    private lateinit var txtInputTitle: TextInputLayout
    private lateinit var txtInputReference: TextInputLayout
    private lateinit var txtInputBrand: TextInputLayout
    private lateinit var txtInputModel: TextInputLayout
    private lateinit var txtInputLab: TextInputLayout

    private lateinit var editTitle: TextInputEditText
    private lateinit var editReference: TextInputEditText
    private lateinit var editBrand: TextInputEditText
    private lateinit var editModel: TextInputEditText
    private lateinit var editLab: TextInputEditText


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle? ): View? {

        val view = inflater.inflate(R.layout.fragment_add_hardware, container, false)


        txtInputTitle = view.findViewById(R.id.add_hardware_textInput_title)
        txtInputReference = view.findViewById(R.id.add_hardware_textInput_reference)
        txtInputBrand = view.findViewById(R.id.add_hardware_textInput_brand)
        txtInputModel = view.findViewById(R.id.add_hardware_textInput_model)
        txtInputLab = view.findViewById(R.id.add_hardware_textInput_lab)

        editTitle = view.findViewById(R.id.add_hardware_title)
        editReference = view.findViewById(R.id.add_hardware_reference)
        editBrand = view.findViewById(R.id.add_hardware_brand)
        editModel = view.findViewById(R.id.add_hardware_model)
        editLab = view.findViewById(R.id.add_hardware_lab)


        view.findViewById<Button>(R.id.btn_add_hardware).setOnClickListener {
            if (checkFields())
            {
                saveNewHardware()
            }
        }

        return view
    }

    private fun saveNewHardware()
    {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage(getString(R.string.msg_loading))
        progressDialog.setCancelable(false)
        progressDialog.show()

        ApiInterface.create().createNewHardware(editTitle.text.toString(), editReference.text.toString(), editBrand.text.toString(), editModel.text.toString(), editLab.text.toString())
            .enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>)
            {
                if (progressDialog.isShowing) progressDialog.dismiss()

                if ( response.code() == 201 )
                {
                    resetFields()
                    Toast.makeText(requireContext(), getString(R.string.msg_hardware_created_successfully), Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseUser>, t: Throwable)
            {
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(requireContext(), "Error: " + t.message.toString(), Toast.LENGTH_LONG).show()
                Log.e("error",t.message.toString())
            }
        })
    }

    private fun checkFields(): Boolean {
        if (editTitle.text.toString().trim().isEmpty()) {
            txtInputTitle.error = getString(R.string.msg_field_must_not_be_empty)
            return false
        }
        else
        {
            txtInputTitle.error = null
        }


        if (editReference.text.toString().isEmpty())
        {
            txtInputReference.error = getString(R.string.msg_field_must_not_be_empty)
            return false
        }
        else
        {
            txtInputReference.error = null
        }

        if (editBrand.text.toString().trim().isEmpty())
        {
            txtInputBrand.error = getString(R.string.msg_field_must_not_be_empty)
            return false
        }
        else
        {
            txtInputBrand.error = null
        }

        if (editModel.text.toString().trim().isEmpty())
        {
            txtInputModel.error = getString(R.string.msg_field_must_not_be_empty)
            return false
        }
        else
        {
            txtInputModel.error = null
        }

        if (editLab.text.toString().trim().isEmpty())
        {
            txtInputLab.error = getString(R.string.msg_field_must_not_be_empty)
            return false
        }
        else
        {
            txtInputLab.error = null
        }

        return true
    }

    private fun resetFields()
    {
        editTitle.setText("")
        editReference.setText("")
        editBrand.setText("")
        editModel.setText("")
        editLab.setText("")
    }
}