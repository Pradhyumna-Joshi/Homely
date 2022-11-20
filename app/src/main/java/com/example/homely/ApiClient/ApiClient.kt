package com.example.homely.ApiClient


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://192.168.64.209/homely/"

class ApiClient {
    companion object{
        var retrofit : Retrofit? =null

        fun getclient() : Retrofit? {

            if(retrofit==null){
                val okHttpClient = OkHttpClient.Builder().build()
                val gson: Gson = GsonBuilder()
                    .setLenient()
                    .create()
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }

            return retrofit
        }
    }

}