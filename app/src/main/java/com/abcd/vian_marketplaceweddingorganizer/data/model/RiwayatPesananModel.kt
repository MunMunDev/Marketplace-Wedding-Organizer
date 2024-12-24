package com.abcd.vian_marketplaceweddingorganizer.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class RiwayatPesananModel (
    @SerializedName("id_riwayat_pesanan")
    val id_riwayat_pesanan: Int? = null,

    @SerializedName("id_user")
    val id_user: Int? = null,

    @SerializedName("wo")
    val wo: WeddingOrganizerModel? = null,

    @SerializedName("vendor")
    val vendor: String? = null,

    @SerializedName("id_pemesanan")
    val id_pemesanan: Int? = null,

    @SerializedName("nama_lengkap")
    val nama_lengkap: String? = null,

    @SerializedName("nomor_hp")
    val nomor_hp: String? = null,

    @SerializedName("kecamatan_kab_kota")
    val kecamatan_kab_kota: String? = null,

    @SerializedName("alamat")
    val alamat: String? = null,

    @SerializedName("detail_alamat")
    val detail_alamat: String? = null,

    @SerializedName("harga")
    val harga: Int? = null,

    @SerializedName("metode_pembayaran")
    val metode_pembayaran: String? = null,

    @SerializedName("ket")
    val ket: String? = null,

    @SerializedName("selesai")
    val selesai: String? = null,

    @SerializedName("waktu")
    val waktu: String? = null,

    @SerializedName("waktu_acara")
    val waktu_acara: String? = null,

    @SerializedName("waktu_bayar")
    val waktu_bayar: String? = null,
)