package com.abcd.vian_marketplaceweddingorganizer.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class VendorModel (
    @SerializedName("id_vendor")
    val id_vendor: Int? = null,

    @SerializedName("nama_vendor")
    val nama_vendor: String? = null,

    @SerializedName("deskripsi_vendor")
    val deskripsi_vendor: String? = null,

    @SerializedName("harga")
    val harga: Int? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_vendor)
        parcel.writeString(nama_vendor)
        parcel.writeString(deskripsi_vendor)
        parcel.writeValue(harga)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VendorModel> {
        override fun createFromParcel(parcel: Parcel): VendorModel {
            return VendorModel(parcel)
        }

        override fun newArray(size: Int): Array<VendorModel?> {
            return arrayOfNulls(size)
        }
    }
}