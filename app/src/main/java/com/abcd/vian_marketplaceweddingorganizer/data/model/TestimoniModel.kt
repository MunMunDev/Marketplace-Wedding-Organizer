package com.abcd.vian_marketplaceweddingorganizer.data.model

import com.google.gson.annotations.SerializedName

class TestimoniModel (
    @SerializedName("id_testimoni")
    var id_testimoni: String? = null,

    @SerializedName("id_pemesanan")
    var id_pemesanan: String? = null,

    @SerializedName("id_user")
    var id_user: String? = null,

    @SerializedName("nama_user")
    var nama_user: String? = null,

    @SerializedName("vendor")
    var vendor: String? = null,

    @SerializedName("id_wo")
    var id_wo: String? = null,

    @SerializedName("testimoni")
    var testimoni: String? = null,

    @SerializedName("bintang")
    var bintang: String? = null,

    @SerializedName("tanggal")
    var tanggal: String? = null,
)