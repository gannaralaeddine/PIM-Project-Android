package com.example.pim_project

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.pim_project.model.LoginRequest
import com.example.pim_project.model.ResponseUser
import com.example.pim_project.model.User
import com.example.pim_project.utils.ApiInterface
import com.example.pim_project.utils.RetrofitClient
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity()
{
    private lateinit var txtInputEmail: TextInputLayout
    private lateinit var txtInputpassword: TextInputLayout
    private lateinit var editTextEmail: EditText
    private lateinit var editTextpassword: EditText
    private lateinit var btn_login: Button
    private lateinit var rememberMe: CheckBox

// Shared Preferences
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    //Retofit
    lateinit var apiInterface: ApiInterface
    internal var compositeDisposable = CompositeDisposable()

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Init Shared Preferences
        sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        //Init API
        val retrofit = RetrofitClient.getInstance()
        apiInterface = retrofit.create(ApiInterface::class.java)

//Finding Views
        txtInputEmail = findViewById(R.id.signIn_txtInput_email)
        txtInputpassword = findViewById(R.id.signIn_txtInput_password)
        editTextEmail = findViewById(R.id.signIn_email)
        editTextpassword = findViewById(R.id.signIn_password)
        btn_login = findViewById(R.id.btn_signIn_login)
        rememberMe = findViewById(R.id.signIn_rememberMe)


        findViewById<TextView>(R.id.login_forgotPassword).setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        findViewById<TextView>(R.id.login_createAccount).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btn_login.setOnClickListener{

            if (checkFields())
            {
                loginUser(editTextEmail.text.toString(), editTextpassword.text.toString())
            }
        }
    }

    private fun loginUser(email: String, password: String)
    {
        val api = ApiInterface.create()

        api.loginUser(email, password).enqueue(object: Callback<ResponseUser> {

            override fun onResponse(call: Call<ResponseUser>, res: Response<ResponseUser>) {

                if (res.code() == 200)
                {
                    editor.putBoolean("isChecked", rememberMe.isChecked)
                    editor.putString("token", res.body()!!.token)
                    editor.putString("idUser", res.body()!!.user._id)
                    editor.apply()

                    Toast.makeText(this@LoginActivity, "Successfully connected", Toast.LENGTH_SHORT).show()
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
        if (editTextEmail.text.toString().isEmpty()) {
            txtInputEmail.error = "Field must not be empty !"
            return false
        }
        else
        {
            txtInputEmail.error = null
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches()) {
            txtInputEmail.error = "E-mail not valid !"
            return false
        }
        else
        {
            txtInputEmail.error = null
        }

        if (editTextpassword.text.toString().isEmpty())
        {
            txtInputpassword.error = "Field must not be empty !"
            return false
        }
        else
        {
            txtInputpassword.error = null
        }

        if (editTextpassword.text.toString().length < 6)
        {
            txtInputpassword.error = "Password must have at least 6 characters !"
            return false
        }
        else
        {
            txtInputpassword.error = null
        }

        return true
    }
}