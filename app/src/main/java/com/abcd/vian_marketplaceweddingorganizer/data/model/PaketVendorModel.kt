package com.abcd.vian_marketplaceweddingorganizer.data.model

import com.google.gson.annotations.SerializedName

class PaketVendorModel (
    @SerializedName("id_paket_vendor")
    var id_paket_vendor: Int?,

    @SerializedName("id_paket")
    var id_paket: String?,

    @SerializedName("id_vendor")
    var id_vendor: String?,

    @SerializedName("vendor")
    var vendor: VendorModel?,

)