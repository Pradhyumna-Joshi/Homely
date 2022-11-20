package com.example.homely.Models

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.ParcelField


data class Facilities(

    var name : String,
    var street : String,
    var city : String,
    var state : String,
    var landmark : String,
    var zipcode : String,

    val typeRoom : Int,
    val typePG : Int,

    val share1 : Int,
    val share2 : Int,
    val share3 : Int,
    val share4 : Int,

    val wifi : Int,
    val cleaning : Int,
    val veg : Int,
    val nonVeg : Int,
    val north : Int,
    val south : Int,
    val wash : Int,
    val TV : Int,

    val rent1 : Int,
    val rent2 : Int,
    val rent3 : Int,
    val rent4 : Int,

    val userid : String,

    val imageURL : String


    ) : Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(street)
        parcel.writeString(city)
        parcel.writeString(state)
        parcel.writeString(landmark)
        parcel.writeString(zipcode)
        parcel.writeInt(typeRoom)
        parcel.writeInt(typePG)
        parcel.writeInt(share1)
        parcel.writeInt(share2)
        parcel.writeInt(share3)
        parcel.writeInt(share4)
        parcel.writeInt(wifi)
        parcel.writeInt(cleaning)
        parcel.writeInt(veg)
        parcel.writeInt(nonVeg)
        parcel.writeInt(north)
        parcel.writeInt(south)
        parcel.writeInt(wash)
        parcel.writeInt(TV)
        parcel.writeInt(rent1)
        parcel.writeInt(rent2)
        parcel.writeInt(rent3)
        parcel.writeInt(rent4)
        parcel.writeString(userid)
        parcel.writeString(imageURL)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Facilities> {
        override fun createFromParcel(parcel: Parcel): Facilities {
            return Facilities(parcel)
        }

        override fun newArray(size: Int): Array<Facilities?> {
            return arrayOfNulls(size)
        }
    }


}