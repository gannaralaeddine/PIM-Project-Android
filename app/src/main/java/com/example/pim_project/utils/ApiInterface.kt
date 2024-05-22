package com.example.pim_project.utils

import com.example.pim_project.model.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface
{

// Authentication
    @POST("login")
    fun loginUser(@Query("email")email: String, @Query("password")password: String): Call<ResponseUser>

    @POST("register")
    fun registerUser(
        @Query("email") email: String,
        @Query("firstName") firstName: String,
        @Query("lastName") lastName: String,
        @Query("espritIdentifier") espritIdentifier: String,
        @Query("classroom") classroom: String,
        @Query("password") password: String
    ): Call<ResponseUser>


    @POST("/forgot-password")
    fun forgotPassword(@Query("email")email: String, @Query("code")code: String): Call<ResponseUser>

    @PUT("reset-password")
    fun resetPassword(@Query("email")email: String, @Query("password")password: String): Call<ResponseUser>


    @PUT("user/update/profile")
    fun updateProfile(@Query("email") email: String,
                      @Query("firstName") firstName: String,
                      @Query("lastName") lastName: String,
                      @Query("espritIdentifier") espritIdentifier: String,
                      @Query("classroom") classroom: String): Call<ResponseUser>


    @GET("hardware/list")
    fun getHardwareList(): Call<List<Hardware>>

    @POST("hardware/create")
    fun createNewHardware(@Query("title")title: String, @Query("reference")reference: String, @Query("brand")brand: String, @Query("model")model: String, @Query("lab")lab: String): Call<ResponseUser>


    @GET("user/getUserById")
    fun getUser(@Query("id")id: String): Call<ResponseUser>



    @POST("booking/hardware-booking")
    fun hardwareBooking(@Query("userId") userId:String, @Query("harwareId") harwareId: String, @Query("date") date: String, @Query("time") time: String
               ): Call<ResponseUser>


    @GET("hardware/getHardware")
    fun getHardware(@Query("id")id: String): Call<Hardware>


    @GET("booking/my-booking-list")
    fun getMyBookingList(@Query("userId")userId: String): Call<List<Booking>>

    @GET("booking/booking-list")
    fun getBookingList(): Call<List<Booking>>



//********************************************************


    companion object
    {
        fun create(): ApiInterface
        {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Conf.BASE_URL).build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}