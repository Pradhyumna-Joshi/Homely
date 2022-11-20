package com.example.homely.ApiClient

import com.example.homely.Models.Response
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

//http://192.168.177.212/homely/owner.php


interface StayInterface {

    @FormUrlEncoded
    @POST("owner.php")
    fun addOwner(
        @Field("name") name: String,
        @Field("dob") dob: String,
        @Field("email") email: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("id") id : Int
        ) : Call<Response>

}


object StayService{

    val stayinstance : StayInterface
    val okHttpClient = OkHttpClient.Builder().build()
    val gson: Gson = GsonBuilder()
    .setLenient()
    .create()
    init{


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        stayinstance=retrofit.create(StayInterface::class.java)
    }




}