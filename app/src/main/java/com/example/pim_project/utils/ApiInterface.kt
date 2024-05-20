package com.example.pim_project.utils

import com.example.pim_project.model.*
import io.reactivex.Observable
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

    @POST("reset-password")
    fun logOut(@Query("email")email: String, @Query("password")password: String): Call<ResponseUser>


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

    //********************************************************

    @POST("lab/add")
    @FormUrlEncoded
    fun labAdd(@Field("title") title:String, @Field("number") number: String): Observable<String>




    @GET("user/getUserHikings")
    fun getMyHikings(@Query("id")id: String): Call<ResponseUser>



    // Hardware
    @GET("hardware/list")
    fun getHikings(): Call<List<Hardware>>


    @PUT("hiking/participate")
    fun hikingParticipate(@Query("id")userId: String?, @Query("hikingId")hikingId: String): Call<ResponseUser>



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