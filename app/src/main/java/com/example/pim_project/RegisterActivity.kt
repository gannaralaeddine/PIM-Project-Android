package com.example.pim_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.pim_project.model.ResponseUser
import com.example.pim_project.utils.ApiInterface
import com.example.pim_project.utils.RetrofitClient
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

class RegisterActivity : AppCompatActivity()
{
    private lateinit var txtInputEmail: TextInputLayout
    private lateinit var txtInputFirstname: TextInputLayout
    private lateinit var txtInputLastname: TextInputLayout
    private lateinit var txtInputEspritIdentifier: TextInputLayout
    private lateinit var txtInputEspritClassroom: TextInputLayout
    private lateinit var txtInputPassword: TextInputLayout
    private lateinit var txtInputConfirmPassword: TextInputLayout

    private lateinit var editTextEmail: EditText
    private lateinit var editTextFirstname: EditText
    private lateinit var editTextLastname: EditText
    private lateinit var editTextEspritIdentifier: EditText
    private lateinit var editTextClassroom: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText


    //Retrofit
    private lateinit var apiInterface: ApiInterface
    internal var compositeDisposable = CompositeDisposable()

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setTitle(R.string.register)


// To enable the back button in your app use
        actionBar?.setHomeButtonEnabled(true);
        actionBar?.setDisplayHomeAsUpEnabled(true);

        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select birth date").build()

// Mapping TextInput
        txtInputEmail = findViewById(R.id.register_textInput_email)
        txtInputFirstname = findViewById(R.id.register_textInput_firstname)
        txtInputLastname = findViewById(R.id.register_textInput_lastname)
        txtInputEspritIdentifier = findViewById(R.id.register_textInput_identifier)
        txtInputEspritClassroom = findViewById(R.id.register_textInput_class)
        txtInputPassword = findViewById(R.id.register_txtInput_password)
        txtInputConfirmPassword = findViewById(R.id.register_txtInput_confirm_password)

// Mapping EditText
        editTextEmail = findViewById(R.id.register_email)
        editTextFirstname = findViewById(R.id.register_firstname)
        editTextLastname = findViewById(R.id.register_lastname)
        editTextEspritIdentifier = findViewById(R.id.register_identifier)
        editTextClassroom = findViewById(R.id.register_class)
        editTextPassword = findViewById(R.id.register_password)
        editTextConfirmPassword = findViewById(R.id.register_confirm_password)
        editTextClassroom = findViewById(R.id.register_class)


        // Check text input
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

        editTextFirstname.doOnTextChanged { text, start, before, count ->
            if (text!!.length > 20)
            {
                txtInputFirstname.error = "No More Remove one character please!"
            }
            else
            {
                txtInputFirstname.error = null
            }
        }

        editTextLastname.doOnTextChanged { text, start, before, count ->
            if (text!!.length > 20)
            {
                txtInputLastname.error = "No More Remove one character please!"
            }
            else
            {
                txtInputLastname.error = null
            }
        }

        editTextEspritIdentifier.doOnTextChanged { text, start, before, count ->
            if (text!!.length != 10)
            {
                txtInputEspritIdentifier.error = "Identifier must have 10 characters !"
            }
            else
            {
                txtInputEspritIdentifier.error = null
            }
        }

        editTextClassroom.doOnTextChanged { text, start, before, count ->
            if (text!!.length > 10)
            {
                txtInputEspritClassroom.error = "No More Remove one character please!"
            }
            else
            {
                txtInputEspritClassroom.error = null
            }
        }

        editTextPassword.doOnTextChanged { text, start, before, count ->
            if (text!!.length < 6)
            {
                txtInputPassword.error = "Password must have at least 6 characters !"
            }
            else
            {
                txtInputPassword.error = null
            }
        }



        findViewById<Button>(R.id.btn_register_register).setOnClickListener {

            if (checkFields())
            {
                register(editTextEmail.text.toString(), editTextFirstname.text.toString(), editTextLastname.text.toString(),
                    editTextEspritIdentifier.text.toString(), editTextClassroom.text.toString(), editTextPassword.text.toString())
            }
        }
    }

    private fun register(email: String, firstName: String, lastName: String, espritIdentifier: String, classroom: String, password: String)
    {
        val api = ApiInterface.create()

        api.registerUser(email, firstName, lastName, espritIdentifier, classroom, password)
            .enqueue(object: Callback<ResponseUser> {

                override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {

                    if (response.code() == 201)
                    {
                        Toast.makeText(this@RegisterActivity, "Successfully registered", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                        return
                    }

                    if (response.code() == 302)
                    {
                        Toast.makeText(this@RegisterActivity, "User already exist !", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_SHORT).show()
                    println(t.message)
                }
            })
    }

    private fun checkFields(): Boolean
    {
        if (editTextEmail.text.toString().isEmpty())
        {
            txtInputEmail.error = "Field must not be empty !"
            return false
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches())
        {
            txtInputEmail.error = "E-mail not valid !"
            return false
        }


        if (editTextFirstname.text.toString().isEmpty())
        {
            txtInputFirstname.error = "Field must not be empty !"
            return false
        }


        if (editTextLastname.text.toString().isEmpty())
        {
            txtInputLastname.error = "Field must not be empty !"
            return false
        }


        if (editTextEspritIdentifier.text.toString().isEmpty())
        {
            txtInputEspritIdentifier.error = "Field must not be empty !"
            return false
        }


        if (editTextEspritIdentifier.text.toString().length != 10)
        {
            txtInputEspritIdentifier.error = "Identifier must have 10 characters !"
            return false
        }


        if (editTextClassroom.text.toString().isEmpty())
        {
            txtInputEspritClassroom.error = "Field must not be empty !"
            return false
        }


// Passwords check
        if (editTextPassword.text.toString().isEmpty())
        {
            txtInputPassword.error = "Field must not be empty !"
            return false
        }


        if (editTextConfirmPassword.text.toString().isEmpty())
        {
            txtInputConfirmPassword.error = "Field must not be empty !"
            return false
        }


        if (editTextPassword.text.toString().length < 6)
        {
            txtInputPassword.error = "Password must have at least 6 characters !"
            return false
        }


        if (editTextConfirmPassword.text.toString().length < 6)
        {
            txtInputConfirmPassword.error = "Password must have at least 6 characters !"
            return false
        }



        if (editTextPassword.text.toString() != editTextConfirmPassword.text.toString())
        {
            e("pass", editTextPassword.text.toString() + " " + editTextConfirmPassword.text.toString())
            txtInputConfirmPassword.error = "Passwords don't match !"
            return false
        }
        else
        {
            txtInputConfirmPassword.error = null
        }

        return true
    }
}