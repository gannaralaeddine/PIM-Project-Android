package com.example.pim_project

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pim_project.model.ResponseUser
import com.example.pim_project.utils.ApiInterface
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddHardwareActivity : AppCompatActivity()
{
    private lateinit var toolbar: MaterialToolbar

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
    private lateinit var btnAddUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_hardware)

        txtInputTitle = findViewById(R.id.add_hardware_textInput_title)
        txtInputReference = findViewById(R.id.add_hardware_textInput_reference)
        txtInputBrand = findViewById(R.id.add_hardware_textInput_brand)
        txtInputModel = findViewById(R.id.add_hardware_textInput_model)
        txtInputLab = findViewById(R.id.add_hardware_textInput_lab)

        editTitle = findViewById(R.id.add_hardware_title)
        editReference = findViewById(R.id.add_hardware_reference)
        editBrand = findViewById(R.id.add_hardware_brand)
        editModel = findViewById(R.id.add_hardware_model)
        editLab = findViewById(R.id.add_hardware_lab)

        btnAddUpdate = findViewById(R.id.btn_add_hardware)
        toolbar = findViewById(R.id.toolbar)


        val hardwareId = intent.getStringExtra("hardwareId")
        if (hardwareId != null)
        {
            toolbar.title = getString(R.string.update_hardware)
            btnAddUpdate.text = getString(R.string.update)
            editTitle.setText(intent.getStringExtra("hardwareTitle"))
            editReference.setText(intent.getStringExtra("hardwareReference"))
            editBrand.setText(intent.getStringExtra("hardwareBrand"))
            editModel.setText(intent.getStringExtra("hardwareModel"))
            editLab.setText(intent.getStringExtra("hardwareLab"))
        }
        else
        {
            toolbar.title = getString(R.string.create_new_hardware)

        }
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        btnAddUpdate.setOnClickListener {
            if (checkFields())
            {
                if (hardwareId != null) {
                    updateHardware()
                }
                else
                {
                    saveNewHardware()
                }
            }
        }

    }

    private fun updateHardware() {
        val progressDialog = ProgressDialog(this@AddHardwareActivity)
        progressDialog.setMessage(getString(R.string.msg_loading))
        progressDialog.setCancelable(false)
        progressDialog.show()

        ApiInterface.create().updateHardware(intent.getStringExtra("hardwareId").toString(), editTitle.text.toString(), editReference.text.toString(), editBrand.text.toString(), editModel.text.toString(), editLab.text.toString())
            .enqueue(object : Callback<ResponseUser> {
                override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>)
                {
                    if (progressDialog.isShowing) progressDialog.dismiss()

                    if ( response.code() == 200 )
                    {
                        Toast.makeText(this@AddHardwareActivity, getString(R.string.msg_hardware_updated_successfully), Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<ResponseUser>, t: Throwable)
                {
                    if (progressDialog.isShowing) progressDialog.dismiss()
                    Toast.makeText(this@AddHardwareActivity, "Error: " + t.message.toString(), Toast.LENGTH_LONG).show()
                    Log.e("error",t.message.toString())
                }
            })
    }

    private fun saveNewHardware()
    {
        val progressDialog = ProgressDialog(this@AddHardwareActivity)
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
                    Toast.makeText(this@AddHardwareActivity, getString(R.string.msg_hardware_created_successfully), Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseUser>, t: Throwable)
            {
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this@AddHardwareActivity, "Error: " + t.message.toString(), Toast.LENGTH_LONG).show()
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