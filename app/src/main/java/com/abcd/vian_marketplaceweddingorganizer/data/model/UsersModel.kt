package com.abcd.vian_marketplaceweddingorganizer.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class UsersModel (
    @SerializedName("id_user")
    var idUser: Int? = null,

    @SerializedName("id_wo")
    var id_wo: Int? = null,

    @SerializedName("nama")
    var nama: String? = null,

    @SerializedName("alamat")
    var alamat: String? = null,

    @SerializedName("nomor_hp")
    var nomorHp: String? = null,

    @SerializedName("username")
    var username: String? = null,

    @SerializedName("password")
    var password: String? = null,

    @SerializedName("sebagai")
    var sebagai: String? = null,

    // if login wo
    @SerializedName("logo")
    var logo: String? = null,

    @SerializedName("deskripsi")
    var deskripsi: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(idUser)
        parcel.writeValue(id_wo)
        parcel.writeString(nama)
        parcel.writeString(alamat)
        parcel.writeString(nomorHp)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(sebagai)
        parcel.writeString(logo)
        parcel.writeString(deskripsi)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UsersModel> {
        override fun createFromParcel(parcel: Parcel): UsersModel {
            return UsersModel(parcel)
        }

        override fun newArray(size: Int): Array<UsersModel?> {
            return arrayOfNulls(size)
        }
    }
}