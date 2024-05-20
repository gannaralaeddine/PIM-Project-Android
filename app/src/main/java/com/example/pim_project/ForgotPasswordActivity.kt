package com.example.pim_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.pim_project.model.ResponseUser
import com.example.pim_project.utils.ApiInterface
import com.example.pim_project.utils.Conf
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Random

class ForgotPasswordActivity : AppCompatActivity()
{
    private lateinit var txtInputEmail: TextInputLayout
    private lateinit var editTextEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        setTitle(R.string.forgot_password)

//actionbar
        val actionbar = supportActionBar
//set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)

// Binding views
        txtInputEmail = findViewById(R.id.forgotPassword_txtInput_email)
        editTextEmail = findViewById(R.id.forgotPassword_email)

        editTextEmail.doOnTextChanged { text, start, before, count ->
            if (text!!.length > 50)
            {
                txtInputEmail.error = "No More Remove one character please!"
            }
            else
            {
                txtInputEmail.error = null
            }
        }

        findViewById<Button>(R.id.btn_forgotPassword_send).setOnClickListener {
            if(checkFields())
            {
                forgotPassword(editTextEmail.text.trim().toString(), getRandomNumberString())
            }
        }
    }

    private fun getRandomNumberString(): String {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        val rnd = Random()
        val number = rnd.nextInt(999999)

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun forgotPassword(email: String, code: String)
    {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Conf.BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofit = retrofitBuilder.forgotPassword(email, code)

        retrofit.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>)
            {
                Log.e("response code: ", response.code().toString())
                if (response.code() == 200)
                {
                    Toast.makeText(this@ForgotPasswordActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ForgotPasswordActivity, VerificationCodeActivity::class.java)
                    intent.putExtra("email", email)
                    intent.putExtra("code", code)
                    startActivity(intent)
                }
                else if( response.code() == 404 )
                {
                    Toast.makeText(this@ForgotPasswordActivity, "User doesn't exist !!", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this@ForgotPasswordActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                }
            }
            //            gannarala@gmail.com
            override fun onFailure(call: Call<ResponseUser>, t: Throwable)
            {
                Toast.makeText(this@ForgotPasswordActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
                Log.e("error",t.message.toString())
            }

        })
    }

    private fun checkFields(): Boolean
    {
        if (editTextEmail.text.toString().trim().isEmpty()) {
            txtInputEmail.error = getString(R.string.msg_field_must_not_be_empty)
            return false
        }
        else
        {
            txtInputEmail.error = null
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches()) {
            txtInputEmail.error = getString(R.string.msg_email_not_valid)
            return false
        }
        else
        {
            txtInputEmail.error = null
        }
        return true
    }
}