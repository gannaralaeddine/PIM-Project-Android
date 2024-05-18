package com.example.pim_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

class ResetPasswordActivity : AppCompatActivity()
{
    private lateinit var txtInputNewPassword: TextInputLayout
    private lateinit var editTextNewPassword: EditText
    private lateinit var txtInputConfirmNewPassword: TextInputLayout
    private lateinit var editTextConfirmNewPassword: EditText


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        setTitle(R.string.reset_password)

//actionbar
        val actionbar = supportActionBar
//set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)

        txtInputNewPassword = findViewById(R.id.resetPassword_txtInput_password)
        editTextNewPassword = findViewById(R.id.reset_password_password)
        txtInputConfirmNewPassword = findViewById(R.id.reset_password_txtInput_confirmPassword)
        editTextConfirmNewPassword = findViewById(R.id.reset_password_confirmPassword)


        editTextNewPassword.doOnTextChanged { text, start, before, count ->
            if (text!!.length < 6)
            {
                txtInputNewPassword.error = "Password must contains at least 6 characters !"
            }
            else
            {
                txtInputNewPassword.error = null
            }
        }

        editTextConfirmNewPassword.doOnTextChanged { text, start, before, count ->
            if (text!!.length < 6)
            {
                txtInputConfirmNewPassword.error = "Password must contains at least 6 characters !"
            }
            else
            {
                txtInputConfirmNewPassword.error = null
            }
        }

        val email = intent.extras?.getString("email", null).toString()

        findViewById<Button>(R.id.resetPassword_reset).setOnClickListener {
            if ( editTextNewPassword.text.toString() != editTextConfirmNewPassword.text.toString() )
            {
                Toast.makeText(this@ResetPasswordActivity, "Thoes passwords didn't match. Try again.", Toast.LENGTH_LONG).show()
            }
            else if ( editTextNewPassword.text.length < 6 || editTextConfirmNewPassword.text.length < 6)
            {
                Toast.makeText(this@ResetPasswordActivity, "Password must contains at least 6 characters !", Toast.LENGTH_LONG).show()
            }
            else
            {
                Log.e("email reset: ", email)
                resetPassword(email, editTextNewPassword.text.toString())
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun resetPassword(email: String, password: String)
    {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Conf.BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofit = retrofitBuilder.resetPassword(email, password)

        retrofit.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, res: Response<ResponseUser>)
            {
                if (res.code() == 200)
                {
                    Toast.makeText(this@ResetPasswordActivity, "Password updated successfully", Toast.LENGTH_SHORT).show()

                    val intentLogin = Intent(this@ResetPasswordActivity, LoginActivity::class.java)
                    intentLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentLogin)
                    finish()
                    return
                }

                if ( res.code() == 401 )
                {
                    Toast.makeText(this@ResetPasswordActivity, "Error !", Toast.LENGTH_SHORT).show()
                    return
                }
            }
            override fun onFailure(call: Call<ResponseUser>, t: Throwable)
            {
                Toast.makeText(this@ResetPasswordActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
                Log.e("error",t.message.toString())
            }

        })
    }
}