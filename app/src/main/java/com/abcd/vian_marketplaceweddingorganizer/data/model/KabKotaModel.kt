package com.abcd.vian_marketplaceweddingorganizer.data.model

import com.google.gson.annotations.SerializedName

class KabKotaModel (
    @SerializedName("kab_kota")
    var kab_kota: String? = null,

    @SerializedName("kecamatan")
    var listKecamatan: ArrayList<KecamatanModel>? = null
)