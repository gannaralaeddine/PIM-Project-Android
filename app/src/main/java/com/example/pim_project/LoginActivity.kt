package com.example.pim_project

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.pim_project.model.ResponseUser
import com.example.pim_project.utils.ApiInterface
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity()
{
    private lateinit var txtInputEmail: TextInputLayout
    private lateinit var txtInputPassword: TextInputLayout
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var rememberMe: CheckBox

// Shared Preferences
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setTitle(R.string.login)

        //Init Shared Preferences
        sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        editor = sharedPreferences.edit()


//Finding Views
        txtInputEmail = findViewById(R.id.signIn_txtInput_email)
        txtInputPassword = findViewById(R.id.signIn_txtInput_password)
        editTextEmail = findViewById(R.id.signIn_email)
        editTextPassword = findViewById(R.id.signIn_password)
        btnLogin = findViewById(R.id.btn_signIn_login)
        rememberMe = findViewById(R.id.signIn_rememberMe)


        findViewById<TextView>(R.id.login_forgotPassword).setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        findViewById<TextView>(R.id.login_createAccount).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener{

            if (checkFields())
            {
                loginUser(editTextEmail.text.toString(), editTextPassword.text.toString())
            }
        }
    }

    private fun loginUser(email: String, password: String)
    {

        ApiInterface.create().loginUser(email, password).enqueue(object: Callback<ResponseUser> {

            override fun onResponse(call: Call<ResponseUser>, res: Response<ResponseUser>) {

                if (res.code() == 200)
                {
                    editor.putBoolean("isChecked", rememberMe.isChecked)
                    editor.putString("token", res.body()!!.token)
                    editor.putString("userId", res.body()!!.user._id)
                    editor.apply()

                    Toast.makeText(this@LoginActivity, getString(R.string.msg_welcome), Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    return
                }

                if (res.code() == 402)
                {
                    Toast.makeText(this@LoginActivity, "Your account isn't verified, a verification email has been sent to your e-mail !", Toast.LENGTH_LONG).show()
                    return
                }

                if (res.code() == 401)
                {
                    Toast.makeText(this@LoginActivity, "Check your password !", Toast.LENGTH_SHORT).show()
                    return
                }

                if (res.code() == 404)
                {
                    Toast.makeText(this@LoginActivity, "User Not found check your Email address !", Toast.LENGTH_SHORT).show()
                    return
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun checkFields(): Boolean {
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

        if (editTextPassword.text.toString().isEmpty())
        {
            txtInputPassword.error = getString(R.string.msg_field_must_not_be_empty)
            return false
        }
        else
        {
            txtInputPassword.error = null
        }

        if (editTextPassword.text.toString().length < 6)
        {
            txtInputPassword.error = getString(R.string.msg_password_must_have_at_least_6_characters)
            return false
        }
        else
        {
            txtInputPassword.error = null
        }

        return true
    }
}