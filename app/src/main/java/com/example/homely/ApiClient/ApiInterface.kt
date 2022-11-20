package com.example.homely.ApiClient

import androidx.browser.customtabs.CustomTabsService
import com.example.homely.Models.Owner
import com.example.homely.Models.Response
import com.example.homely.Models.Stay
import com.example.homely.Models.getOwner
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("owner.php")
    fun addOwner(
        @Field("name") name: String,
        @Field("dob") dob: String,
        @Field("email") email: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("userid") userid : String,
        @Field("upiid") upiid : String
    ): Call<Response>


    @FormUrlEncoded
    @POST("stay.php")
    fun addStay(
        @Field("name") name: String,
        @Field("street") street: String,
        @Field("city") city: String,
        @Field("state") state: String,
        @Field("landmark") landmark: String,
        @Field("zipcode") zipcode: String,
        @Field("typeRoom") typeRoom: Int,
        @Field("typePG") typePG: Int,
        @Field("share1") share1: Int,
        @Field("share2") share2: Int,
        @Field("share3") share3: Int,
        @Field("share4") share4: Int,
        @Field("wifi") wifi: Int,
        @Field("cleaning") cleaning: Int,
        @Field("veg") veg: Int,
        @Field("nonVeg") nonVeg: Int,
        @Field("north") north: Int,
        @Field("south") south: Int,
        @Field("wash") wash: Int,
        @Field("TV") TV: Int,
        @Field("rent1") rent1: Int,
        @Field("rent2") rent2: Int,
        @Field("rent3") rent3: Int,
        @Field("rent4") rent4: Int,
        @Field("userid") userid : String,
        @Field("imageURL") imageURL : String
    ): Call<Response>


    @GET("getStay.php")
    fun getStay(
        @Query("userid") userid : String
    ): Call<Stay>


    @GET("getStay1.php")
    fun getStay1(
    ): Call<Stay>


    @GET("search.php")
    fun searchStay(
        @Query("query_") query_: String?,
        @Query("typeRoom") typeRoom: Int,
        @Query("typePG") typePG: Int
    ): Call<Stay>

    @FormUrlEncoded
    @POST("update.php")
    fun updateStay(
        @Field("pos") pos: Int,
        @Field("name") name: String,
        @Field("street") street: String,
        @Field("city") city: String,
        @Field("state") state: String,
        @Field("landmark") landmark: String,
        @Field("zipcode") zipcode: String,
        @Field("typeRoom") typeRoom : Int,
        @Field("typePG") typePG: Int,
        @Field("share1") share1: Int,
        @Field("share2") share2: Int,
        @Field("share3") share3: Int,
        @Field("share4") share4: Int,
        @Field("wifi") wifi: Int,
        @Field("cleaning") cleaning: Int,
        @Field("veg") veg: Int,
        @Field("nonVeg") nonVeg: Int,
        @Field("north") north: Int,
        @Field("south") south: Int,
        @Field("wash") wash: Int,
        @Field("TV") TV: Int,
        @Field("rent1") rent1: Int,
        @Field("rent2") rent2: Int,
        @Field("rent3") rent3: Int,
        @Field("rent4") rent4: Int,
        @Field("userid") userid : String,
        @Field("imageURL") imageURL : String

    ): Call<Response>

    @FormUrlEncoded
    @POST("delete.php")
    fun deleteStay(
        @Field("id") id : Int
    ):Call<Response>



    @GET("getOwner.php")
    fun getOwner(
        @Query("userid") userid : String
    ):Call<getOwner>

}