package com.example.pim_project.utils

import com.example.pim_project.model.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

//********************************************************


    @POST("lab/add")
    @FormUrlEncoded
    fun labAdd(@Field("title") title:String, @Field("number") number: String): Observable<String>




    // Forgot password
    @POST("/forgot-password")
    fun forgotPassword(@Query("email")email: String, @Query("code")code: String): Call<ResponseUser>

    @PUT("reset-password")
    fun resetPassword(@Query("email")email: String, @Query("password")password: String): Call<ResponseUser>


    @GET("user/getUserById")
    fun getUser(@Query("id")id: String): Call<User>

    @Multipart
    @PUT("user/updateProfileImage")
    fun updateProfileImage(@Part image: MultipartBody.Part, @Part("desc") desc: RequestBody): Call<ResponseUser>

    @GET("user/getUserHikings")
    fun getMyHikings(@Query("id")id: String): Call<ResponseUser>



    // Hardware
    @GET("hardware/list")
    fun getHikings(): Call<List<Hardware>>










    @PUT("hiking/participate")
    fun hikingParticipate(@Query("id")userId: String?, @Query("hikingId")hikingId: String): Call<ResponseUser>

    @POST("hiking/add")
    fun hikingAdd(@Query("title")title: String, @Query("organizer")organizer: String, @Query("program")program: String, @Query("id")idUser: String): Call<ResponseUser>


    companion object
    {
        var BASE_URL = "http://192.168.1.246:5000/"

        fun create(): ApiInterface
        {
            val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}