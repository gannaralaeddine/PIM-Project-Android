package com.example.pim_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout

class VerificationCodeActivity : AppCompatActivity()
{

    private lateinit var txtInputCode: TextInputLayout
    private lateinit var editTextCode: EditText

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_code)

        setTitle(R.string.verification)

//actionbar
        val actionbar = supportActionBar
//set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)

// Binding views
        txtInputCode = findViewById(R.id.verificationCode_txtInput_code)
        editTextCode = findViewById(R.id.verificationCode_edit_code)


        editTextCode.doOnTextChanged { text, start, before, count ->
            if (text!!.length < 6)
            {
                txtInputCode.error = "Password must contains 6 digits !"
            }
            else
            {
                txtInputCode.error = null
            }
        }

        val mail = intent.extras?.getString("email", null)

        findViewById<Button>(R.id.btn_verificationCode_verify).setOnClickListener {
            if( editTextCode.text.trim().length != 6 )
            {
                txtInputCode.error = "Code must be 6 digits !"
            }
            else
            {
                txtInputCode.error = null
                if (intent.extras?.getString("code", null).equals(editTextCode.text.toString()))
                {
                    val intent = Intent(this, ResetPasswordActivity::class.java)

                    intent.putExtra("email", mail)
                    Log.e("email verif: ", mail.toString())

                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this, "Wrong code, please check your inbox !", Toast.LENGTH_SHORT).show()
                }

            }
        }

        Log.e("code: ", intent.extras?.getString("code", null).toString())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}