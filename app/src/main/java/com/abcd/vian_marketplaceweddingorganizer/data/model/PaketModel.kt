package com.abcd.vian_marketplaceweddingorganizer.data.model

import com.google.gson.annotations.SerializedName

class PaketModel (
    @SerializedName("id_paket")
    var id_paket: Int?,

    @SerializedName("nama_paket")
    var nama_paket: String?,

    @SerializedName("keterangan")
    var keterangan: String?,

    @SerializedName("potongan")
    var potongan: Int?,

    @SerializedName("paket_vendor")
    var paket_vendor: ArrayList<PaketVendorModel>?,

)