package com.abcd.vian_marketplaceweddingorganizer.data.model

import com.google.gson.annotations.SerializedName

class RekeningModel (
    @SerializedName("id_rekening")
    var id_rekening: String? = null,

    @SerializedName("id_user")
    var id_user: String? = null,

    @SerializedName("jenis_rekening")
    var jenis_rekening: String? = null,

    @SerializedName("no_rekening")
    var no_rekening: String? = null,

    @SerializedName("nama")
    var nama: String? = null,

    @SerializedName("user")
    var user: KecamatanModel? = null,
)