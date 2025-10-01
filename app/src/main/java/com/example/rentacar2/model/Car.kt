package com.example.rentacar2.model

import android.os.Parcel
import android.os.Parcelable

data class Car(
    val id: Int,
    val name: String,
    val model: String,
    val year: Int,
    val rating: Float,
    val kilometers: Int,
    val dailyCost: Int,
    val imageResId: Int,
    var isAvailable: Boolean = true,
    var isFavorite: Boolean = false
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(model)
        parcel.writeInt(year)
        parcel.writeFloat(rating)
        parcel.writeInt(kilometers)
        parcel.writeInt(dailyCost)
        parcel.writeInt(imageResId)
        parcel.writeByte(if (isAvailable) 1 else 0)
        parcel.writeByte(if (isFavorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Car> {
        override fun createFromParcel(parcel: Parcel): Car {
            return Car(parcel)
        }

        override fun newArray(size: Int): Array<Car?> {
            return arrayOfNulls(size)
        }
    }

    fun getFullName(): String = "$name $model"
    
    fun getFormattedKilometers(): String = "${kilometers}km"
    
    fun getFormattedCost(): String = "$dailyCost credits/day"
}
