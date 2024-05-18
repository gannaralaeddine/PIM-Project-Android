package com.example.pim_project.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Conf
{
    companion object {

//        const val BASE_URL = "https://pim-project-chi.vercel.app/"
//        const val BASE_URL = "http://192.168.1.246:5000/"
        const val BASE_URL = "http://192.168.1.246:5000/"
//        const val BASE_URL = "http://10.0.2.2:5000/"

        private fun createRetrofitInstance(): Retrofit {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS) // Adjust connection timeout
                .readTimeout(30, TimeUnit.SECONDS)    // Adjust read timeout
                .writeTimeout(30, TimeUnit.SECONDS)   // Adjust write timeout
                .build()

            return Retrofit.Builder()
                .baseUrl("https://pim-project-chi.vercel.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
        }
    }
}