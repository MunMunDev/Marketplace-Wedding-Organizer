package com.abcd.vian_marketplaceweddingorganizer.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class MessageModel(
    @SerializedName("id_message")
    var idMessage: Int? = null,

    @SerializedName("message")
    var message: String? = null,

    @SerializedName("gambar")
    var gambar: String? = null,

    @SerializedName("id_user")
    var idUser: Int? = null,

    @SerializedName("id_wo")
    var idWo: Int? = null,

    @SerializedName("pengirim")
    var pengirim: String? = null,

    @SerializedName("tanggal")
    var tanggal: String? = null,

    @SerializedName("waktu")
    var waktu: String? = null,

    @SerializedName("ket")
    var ket: String? = null,

    @SerializedName("wedding_organizer")
    var wedding_organizer: WeddingOrganizerModel? = null,

    @SerializedName("user")
    var user: UsersModel? = null,

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("wedding_organizer"),
        parcel.readParcelable(UsersModel::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(idMessage)
        parcel.writeString(message)
        parcel.writeString(gambar)
        parcel.writeValue(idUser)
        parcel.writeValue(idWo)
        parcel.writeString(pengirim)
        parcel.writeString(tanggal)
        parcel.writeString(waktu)
        parcel.writeString(ket)
        parcel.writeParcelable(user, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageModel> {
        override fun createFromParcel(parcel: Parcel): MessageModel {
            return MessageModel(parcel)
        }

        override fun newArray(size: Int): Array<MessageModel?> {
            return arrayOfNulls(size)
        }
    }
}