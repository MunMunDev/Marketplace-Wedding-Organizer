package com.abcd.vian_marketplaceweddingorganizer.data.model

import com.google.gson.annotations.SerializedName

class PesananModel(
    @SerializedName("id_pemesanan")
    val id_pemesanan: String? = null,

    @SerializedName("id_user")
    val idUser: String? = null,

    @SerializedName("nama_lengkap")
    val nama_lengkap: String? = null,

    @SerializedName("nomor_hp")
    val nomor_hp: String? = null,

    @SerializedName("alamat")
    val alamat: String? = null,

    @SerializedName("harga")
    val harga: String? = null,

    @SerializedName("wo")
    val wo: String? = null,

    @SerializedName("vendor")
    val vendor: String? = null,

    @SerializedName("waktu")
    val waktu: String? = null,

    @SerializedName("metode_pembayaran")
    val metode_pembayaran: KabKotaModel? = null,

    @SerializedName("ket")
    val ket: KabKotaModel? = null,
)