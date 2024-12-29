package com.abcd.vian_marketplaceweddingorganizer.data.model

import com.google.gson.annotations.SerializedName

class WeddingOrganizerModel (
    @SerializedName("id_wo")
    val id_wo: Int? = null,

    @SerializedName("nama")
    val nama: String? = null,

    @SerializedName("deskripsi_wo")
    val deskripsi_wo: String? = null,

    @SerializedName("alamat")
    val alamat: String? = null,

    @SerializedName("harga_wo")
    val harga_wo: Int? = null,

    @SerializedName("logo_wo")
    val logo_wo: String? = null,

    @SerializedName("vendor")
    val vendor: ArrayList<VendorModel> = arrayListOf(),
)