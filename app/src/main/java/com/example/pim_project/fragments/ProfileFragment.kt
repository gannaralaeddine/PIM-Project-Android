package com.example.pim_project.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.pim_project.LoginActivity
import com.example.pim_project.R
import com.example.pim_project.UpdateProfileActivity
import com.example.pim_project.model.ResponseUser
import com.example.pim_project.utils.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment : Fragment()
{
    private lateinit var profileName: TextView
    private lateinit var profileEmail: TextView

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        profileName = view.findViewById(R.id.profileName_profileFrag)
        profileEmail = view.findViewById(R.id.profileEmail_profileFrag)

        sharedPreferences = requireActivity().getSharedPreferences("prefs", AppCompatActivity.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        getProfileData(sharedPreferences.getString("userId", null))

        view.findViewById<Button>(R.id.btn_log_out).setOnClickListener {
            editor.putBoolean("isChecked", false)
            editor.putString("token", null)
            editor.putString("isUser", null)
            editor.apply()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }

        view.findViewById<CardView>(R.id.profile_fragment_edit_profile).setOnClickListener {
            startActivity(Intent(context, UpdateProfileActivity::class.java))
        }

        return view
    }

    private fun getProfileData(userId: String?)
    {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage(getString(R.string.msg_loading))
        progressDialog.setCancelable(false)
        progressDialog.show()


        if (userId != null)
        {
            ApiInterface.create().getUser(userId).enqueue(object: Callback<ResponseUser> {

                override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                    if (progressDialog.isShowing) progressDialog.dismiss()

                    if (response.code() == 200)
                    {
                        profileEmail.text = response.body()!!.user.email
                        profileName.text = getString(R.string.user_full_name, response.body()!!.user.firstName, response.body()!!.user.lastName)
                        return
                    }

                    if (response.code() == 404)
                    {
                        Toast.makeText(requireContext(),getString(R.string.msg_error_getting_user_data), Toast.LENGTH_SHORT).show()
                        return
                    }

                }
                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    if (progressDialog.isShowing) progressDialog.dismiss()
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
        else
        {
            Toast.makeText(requireContext(),getString(R.string.msg_error_getting_user_data), Toast.LENGTH_SHORT).show()
        }


    }

}