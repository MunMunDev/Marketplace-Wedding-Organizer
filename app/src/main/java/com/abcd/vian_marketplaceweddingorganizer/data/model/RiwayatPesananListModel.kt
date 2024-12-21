package com.abcd.vian_marketplaceweddingorganizer.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class RiwayatPesananListModel (
    @SerializedName("id_pemesanan")
    val id_pemesanan: Int? = null,

    @SerializedName("id_user")
    val id_user: Int? = null,

    @SerializedName("nama_lengkap")
    val nama_lengkap: String? = null,

    @SerializedName("nomor_hp")
    val nomor_hp: String? = null,

    @SerializedName("alamat")
    val alamat: String? = null,

    @SerializedName("harga")
    val harga: Int? = null,

    @SerializedName("wo")
    val wo: String? = null,

    @SerializedName("vendor")
    val vendor: String? = null,

    @SerializedName("waktu")
    val waktu: String? = null,

    @SerializedName("metode_pembayaran")
    val metode_pembayaran: String? = null,

    @SerializedName("ket")
    val ket: String? = null,
)