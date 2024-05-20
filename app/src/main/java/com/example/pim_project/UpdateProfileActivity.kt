package com.example.pim_project

import android.app.ProgressDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.pim_project.model.ResponseUser
import com.example.pim_project.utils.ApiInterface
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProfileActivity : AppCompatActivity()
{
    private lateinit var toolbar: MaterialToolbar

    private lateinit var txtInputEmail: TextInputLayout
    private lateinit var txtInputFirstname: TextInputLayout
    private lateinit var txtInputLastname: TextInputLayout
    private lateinit var txtInputEspritIdentifier: TextInputLayout
    private lateinit var txtInputEspritClassroom: TextInputLayout

    private lateinit var editTextEmail: EditText
    private lateinit var editTextFirstname: EditText
    private lateinit var editTextLastname: EditText
    private lateinit var editTextEspritIdentifier: EditText
    private lateinit var editTextClassroom: EditText

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.edit_profile)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)

        userId = sharedPreferences.getString("userId", null).toString()


        txtInputEmail = findViewById(R.id.editProfile_txtInput_email)
        txtInputFirstname = findViewById(R.id.editProfile_txtInput_firstname)
        txtInputLastname = findViewById(R.id.editProfile_txtInput_lastname)
        txtInputEspritIdentifier = findViewById(R.id.editProfile_txtInput_identifier)
        txtInputEspritClassroom = findViewById(R.id.editProfile_txtInput_class)

        editTextEmail = findViewById(R.id.editProfile_edit_email)
        editTextFirstname = findViewById(R.id.editProfile_edit_firstname)
        editTextLastname = findViewById(R.id.editProfile_edit_lastname)
        editTextEspritIdentifier = findViewById(R.id.editProfile_edit_identifier)
        editTextClassroom = findViewById(R.id.editProfile_edit_class)


        editTextEmail.isEnabled = false


        editTextFirstname.doOnTextChanged { text, start, before, count ->
            if (text!!.length > 20)
            {
                txtInputFirstname.error = getString(R.string.msg_no_more_remove_one_character_please)
            }
            else
            {
                txtInputFirstname.error = null
            }
        }

        editTextLastname.doOnTextChanged { text, start, before, count ->
            if (text!!.length > 20)
            {
                txtInputLastname.error = getString(R.string.msg_no_more_remove_one_character_please)
            }
            else
            {
                txtInputLastname.error = null
            }
        }

        editTextEspritIdentifier.doOnTextChanged { text, start, before, count ->
            if (text!!.length != 10)
            {
                txtInputEspritIdentifier.error = getString(R.string.msg_identifier_must_have_10_characters)
            }
            else
            {
                txtInputEspritIdentifier.error = null
            }
        }

        editTextClassroom.doOnTextChanged { text, start, before, count ->
            if (text!!.length > 10)
            {
                txtInputEspritClassroom.error = getString(R.string.msg_no_more_remove_one_character_please)
            }
            else
            {
                txtInputEspritClassroom.error = null
            }
        }


        getUserData()


        findViewById<Button>(R.id.btn_editProfile_save).setOnClickListener {
            if (checkFields())
            {
                updateUserData()
            }
        }
    }



    private fun updateUserData()
    {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage(getString(R.string.updating_data))
        progressDialog.setCancelable(false)
        progressDialog.show()


        ApiInterface.create().updateProfile(editTextEmail.text.toString(), editTextFirstname.text.toString(), editTextLastname.text.toString(),
            editTextEspritIdentifier.text.toString(), editTextClassroom.text.toString())
            .enqueue(object: Callback<ResponseUser> {

                override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                    if (progressDialog.isShowing) progressDialog.dismiss()

                    if (response.code() == 200)
                    {
                        Toast.makeText(this@UpdateProfileActivity, getString(R.string.msg_data_updated_successfully), Toast.LENGTH_SHORT).show()
                        return
                    }

                }
                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    if (progressDialog.isShowing) progressDialog.dismiss()
                    Toast.makeText(this@UpdateProfileActivity, t.message, Toast.LENGTH_SHORT).show()
                    println(t.message)
                }
            })
    }




    private fun getUserData()
    {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage(getString(R.string.msg_loading))
        progressDialog.setCancelable(false)
        progressDialog.show()


        ApiInterface.create().getUser(userId).enqueue(object: Callback<ResponseUser> {

                override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                    if (progressDialog.isShowing) progressDialog.dismiss()

                    if (response.code() == 200)
                    {
                        editTextEmail.setText(response.body()!!.user.email)
                        editTextFirstname.setText(response.body()!!.user.firstName)
                        editTextLastname.setText(response.body()!!.user.lastName)
                        editTextEspritIdentifier.setText(response.body()!!.user.espritIdentifier)
                        editTextClassroom.setText(response.body()!!.user.classroom)
                        return
                    }

                    if (response.code() == 404)
                    {
                        Toast.makeText(this@UpdateProfileActivity,"User not found !", Toast.LENGTH_SHORT).show()
                        return
                    }

                }
                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    if (progressDialog.isShowing) progressDialog.dismiss()
                    Toast.makeText(this@UpdateProfileActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })

    }



    private fun checkFields(): Boolean
    {
        if (editTextFirstname.text.toString().trim().isEmpty())
        {
            txtInputFirstname.error = getString(R.string.msg_field_must_not_be_empty)
            return false
        }


        if (editTextLastname.text.toString().trim().isEmpty())
        {
            txtInputLastname.error = getString(R.string.msg_field_must_not_be_empty)
            return false
        }


        if (editTextEspritIdentifier.text.toString().trim().isEmpty())
        {
            txtInputEspritIdentifier.error = getString(R.string.msg_field_must_not_be_empty)
            return false
        }


        if (editTextEspritIdentifier.text.toString().trim().length != 10)
        {
            txtInputEspritIdentifier.error = getString(R.string.msg_identifier_must_have_10_characters)
            return false
        }


        if (editTextClassroom.text.toString().trim().isEmpty())
        {
            txtInputEspritClassroom.error = getString(R.string.msg_field_must_not_be_empty)
            return false
        }
        return true

    }
}