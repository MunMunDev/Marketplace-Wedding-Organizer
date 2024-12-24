package com.abcd.vian_marketplaceweddingorganizer.data.database.api

import com.abcd.vian_marketplaceweddingorganizer.data.model.AlamatModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.KabKotaModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.MessageModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.RiwayatPesananListModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.RiwayatPesananModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.TestimoniModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.UsersModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.WeddingOrganizerModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @GET("marketplace-wo/api/get.php")
    suspend fun getAllUser(
        @Query("all_user") allUser: String
    ): ArrayList<UsersModel>

    @GET("marketplace-wo/api/get.php")
    suspend fun getUser(
        @Query("get_user") getUser: String,
        @Query("username") username: String,
        @Query("password") password: String
    ): ArrayList<UsersModel>

    @GET("marketplace-wo/api/get.php")
    suspend fun getPesanan(
        @Query("get_pesanan") get_pesanan: String,
        @Query("id_user") id_user: Int,
    ): ArrayList<RiwayatPesananListModel>

    @GET("marketplace-wo/api/get.php")
    suspend fun getWeddingOrganizer(
        @Query("get_all_wedding_organizer") getAllWeddingOrganizer: String,
    ): ArrayList<WeddingOrganizerModel>

    @GET("marketplace-wo/api/get.php")
    suspend fun getKabKota(
        @Query("get_kab_kota") get_kab_kota: String,
    ): ArrayList<KabKotaModel>

    @GET("marketplace-wo/api/get.php")
    suspend fun getAlamat(
        @Query("get_alamat_main") get_alamat_main: String,
        @Query("id_user") id_user: Int,
    ): ArrayList<AlamatModel>

    @GET("marketplace-wo/api/get.php")
    suspend fun getAlamatUser(
        @Query("get_alamat") get_alamat: String,
        @Query("id_user") id_user: Int,
    ): ArrayList<AlamatModel>

    @GET("marketplace-wo/api/get.php")
    suspend fun getChatListWeddingOrganizer(
        @Query("get_list_chat") get_list_chat: String,
        @Query("id_user") id_user: Int,
    ): ArrayList<MessageModel>

    @GET("marketplace-wo/api/get.php")
    suspend fun getChatWeddingOrganizer(
        @Query("get_chat_wedding_organizer") get_chat_wedding_organizer: String,
        @Query("id_user") id_user: Int,
        @Query("id_wo") id_wo: Int,
    ): ArrayList<MessageModel>

    @GET("marketplace-wo/api/get.php")
    suspend fun getTestimoni(
        @Query("get_testimoni") get_testimoni: String,
        @Query("id_wo") id_wo: Int
    ): ArrayList<TestimoniModel>

    @GET("marketplace-wo/api/get.php")
    suspend fun getRiwayatPesananList(
        @Query("get_riwayat_pesanan") get_riwayat_pesanan: String,
        @Query("id_user") id_user: Int,
    ): ArrayList<RiwayatPesananListModel>

    @GET("marketplace-wo/api/get.php")
    suspend fun getRiwayatPesananDetail(
        @Query("get_detail_riwayat_pesanan") get_detail_riwayat_pesanan: String,
        @Query("id_pemesanan") id_pemesanan: Int
    ): ArrayList<RiwayatPesananModel>

    @GET("marketplace-wo/api/get.php")
    suspend fun getTestimoniRiwayatPesanan(
        @Query("get_testimoni_riwayat_pesanan") get_testimoni_riwayat_pesanan: String,
        @Query("id_pemesanan") id_pemesanan: Int
    ): ArrayList<TestimoniModel>


    // POST
    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun addUser(
        @Field("add_user") addUser:String,
        @Field("nama") nama:String,
        @Field("alamat") alamat:String,
        @Field("nomor_hp") nomorHp:String,
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("sebagai") sebagai:String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postUpdateUser(
        @Field("update_akun") updateAkun:String,
        @Field("id_user") idUser: String,
        @Field("nama") nama:String,
        @Field("alamat") alamat:String,
        @Field("nomor_hp") nomorHp:String,
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("username_lama") usernameLama: String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postHapusUser(
        @Field("hapus_akun") hapusAkun:String,
        @Field("id_user") idUser: String
    ): ArrayList<ResponseModel>

    // Chat
    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postMessage(
        @Field("post_message") post_message:String,
        @Field("message") message: String,
        @Field("id_user") id_user: Int,
        @Field("id_wo") id_wo: Int,
        @Field("pengirim") pengirim: String,
    ): ArrayList<ResponseModel>

    @Multipart
    @POST("marketplace-wo/api/post.php")
    suspend fun postMessageImage(
        @Part("post_message_image") post_message_image: RequestBody,
        @Part gambar: MultipartBody.Part,
        @Part("nama_image") nama_image: RequestBody,
        @Part("id_user") id_user: RequestBody,
        @Part("id_wo") id_wo: RequestBody,
        @Part("pengirim") pengirim: RequestBody,
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postDeleteMessage(
        @Field("post_delete_message") post_delete_message:String,
        @Field("id_message") id_message: Int,
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postTambahPesanan(
        @Field("tambah_pesanan") tambahPesanan: String,
        @Field("id_plafon") id_plafon: String,
        @Field("id_user") idUser: String,
        @Field("jumlah") jumlah: String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postUpdatePesanan(
        @Field("update_pesanan") updatePesanan:String,
        @Field("id_pesanan") idPesanan: String,
        @Field("jumlah") jumlah: String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postHapusPesanan(
        @Field("hapus_pesanan") hapusPesanan:String,
        @Field("id_pesanan") idPesanan: String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postUpdateMainAlamat(
        @Field("update_main_alamat") update_main_alamat: String,
        @Field("id_alamat") id_alamat: String,
        @Field("id_user") id_user: String,
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postTambahAlamat(
        @Field("tambah_pilih_alamat") tambah_pilih_alamat: String,
        @Field("id_user") id_user: String,
        @Field("nama_lengkap") nama_lengkap: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("id_kecamatan") id_kecamatan: String,
        @Field("alamat") alamat: String,
        @Field("detail_alamat") detail_alamat: String,
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postUpdateAlamat(
        @Field("update_pilih_alamat") update_pilih_alamat: String,
        @Field("id_alamat") id_alamat: String,
        @Field("id_user") id_user: String,
        @Field("nama_lengkap") nama_lengkap: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("id_kecamatan") id_kecamatan: String,
        @Field("alamat") alamat: String,
        @Field("detail_alamat") detail_alamat: String,
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postPesan(
        @Field("post_pesan") postPesan:String,
        @Field("id_user") id_user: String,
        @Field("alamat") alamat: String,
        @Field("metode_pembayaran") metode_pembayaran: String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postPesan(
        @Field("post_pesan") postPesan:String,
        @Field("id_user") id_user: String,
        @Field("nama_lengkap") nama_lengkap: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("alamat") alamat: String,
        @Field("detail_alamat") detail_alamat: String,
        @Field("metode_pembayaran") metode_pembayaran: String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postRegistrasiPembayaran(
        @Field("registrasi_pembayaran") registrasiPembayaran:String,
        @Field("id_pembayaran") id_pembayaran:String,
        @Field("id_user") id_user:String,
        @Field("keterangan") keterangan:String,
        @Field("nama_lengkap") nama_lengkap:String,
        @Field("nomor_hp") nomor_hp:String,
        @Field("kecamatan_kab_kota") kecamatan_kab_kota:String,
        @Field("alamat") alamat:String,
        @Field("detail_alamat") detail_alamat:String,
    ): ArrayList<ResponseModel>

    // Post Jenis Plafon
    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postTambahJenisPlafon(
        @Field("tambah_jenis_plafon") tambahJenisPlafon: String,
        @Field("jenis_plafon") jenis_plafon: String,
        @Field("keunggulan") keunggulan: String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postUpdateJenisPlafon(
        @Field("update_jenis_plafon") updateJenisPlafon: String,
        @Field("id_jenis_plafon") id_jenis_plafon: String,
        @Field("jenis_plafon") jenis_plafon: String,
        @Field("keunggulan") keunggulan: String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postDeleteJenisPlafon(
        @Field("delete_jenis_plafon") delete_jenis_plafon:String,
        @Field("id_jenis_plafon") id_jenis_plafon: String
    ): ArrayList<ResponseModel>

    // Post Plafon
    @Multipart
    @POST("marketplace-wo/api/post.php")
    suspend fun postTambahPlafon(
        @Part("tambah_plafon") tambah_plafon: RequestBody,
        @Part("id_jenis_plafon") id_jenis_plafon: RequestBody,
        @Part("keterangan") keterangan: RequestBody,
        @Part gambar: MultipartBody.Part,
        @Part("stok") stok: RequestBody,
        @Part("harga") harga: RequestBody,
    ): ArrayList<ResponseModel>

    @Multipart
    @POST("marketplace-wo/api/post.php")
    suspend fun postUpdatePlafon(
        @Part("update_plafon") updatePlafon: RequestBody,
        @Part("id_plafon") id_plafon: RequestBody,
        @Part("id_jenis_plafon") id_jenis_plafon: RequestBody,
        @Part("keterangan") keterangan: RequestBody,
        @Part gambar: MultipartBody.Part,
        @Part("stok") stok: RequestBody,
        @Part("harga") harga: RequestBody,
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postUpdatePlafonNoImage(
        @Field("update_plafon_no_image") update_plafon_no_image:String,
        @Field("id_plafon") id_plafon:String,
        @Field("id_jenis_plafon") id_jenis_plafon:String,
        @Field("keterangan") keterangan: String,
        @Field("stok") stok: String,
        @Field("harga") harga:String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postDeletePlafon(
        @Field("delete_plafon") deletePlafon:String,
        @Field("id_plafon") id_plafon:String
    ): ArrayList<ResponseModel>


//    @FormUrlEncoded
//    @POST("marketplace-wo/api/post.php")
//    suspend fun postTambahPesanan(
//        @Field("tambah_pesanan") tambah_pesanan: String,
//        @Field("id_pemesanan") id_pemesanan: String,
//        @Field("id_user") id_user: String,
//        @Field("nama") nama: String,
//        @Field("alamat") alamat: String,
//        @Field("nomor_hp") nomor_hp: String,
//        @Field("jenis_plafon") jenis_plafon: String,
//        @Field("harga") harga: String,
//        @Field("panjang") panjang: String,
//        @Field("lebar") lebar: String,
//        @Field("total_harga") total_harga: String,
//        @Field("waktu") waktu: String,
//        @Field("waktu_konfirmasi") waktu_konfirmasi: String,
//        @Field("metode_pembayaran") metode_pembayaran: String,
//        @Field("waktu_bayar") waktu_bayar: String
//    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postTambahPesananAdmin(
        @Field("tambah_pesanan") tambah_pesanan: String,
        @Field("id_pemesanan") id_pemesanan: String,
        @Field("nama") nama: String,
        @Field("alamat") alamat: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("jenis_plafon") jenis_plafon: String,
        @Field("harga") harga: String,
        @Field("panjang") panjang: String,
        @Field("lebar") lebar: String,
        @Field("total_harga") total_harga: String,
        @Field("waktu") waktu: String,
        @Field("waktu_konfirmasi") waktu_konfirmasi: String
    ): ArrayList<ResponseModel>

//    @FormUrlEncoded
//    @POST("marketplace-wo/api/post.php")
//    suspend fun postUpdatePesanan(
//        @Field("update_pesanan") update_pesanan: String,
//        @Field("id_pesanan") id_pesanan: String,
//        @Field("id_user") id_user: String,
//        @Field("nama") nama: String,
//        @Field("alamat") alamat: String,
//        @Field("nomor_hp") nomor_hp: String,
//        @Field("jenis_plafon") jenis_plafon: String,
//        @Field("harga") harga: String,
//        @Field("panjang") panjang: String,
//        @Field("lebar") lebar: String,
//        @Field("total_harga") total_harga: String,
//        @Field("gambar") gambar: String,
//        @Field("waktu") waktu: String,
//        @Field("waktu_konfirmasi") waktu_konfirmasi: String,
//        @Field("metode_pembayaran") metode_pembayaran: String,
//        @Field("waktu_bayar") waktu_bayar: String
//    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postUpdatePesanan(
        @Field("update_pesanan") update_pesanan: String,
        @Field("id_pesanan") id_pesanan: String,
        @Field("nama") nama: String,
        @Field("alamat") alamat: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("jenis_plafon") jenis_plafon: String,
        @Field("harga") harga: String,
        @Field("panjang") panjang: String,
        @Field("lebar") lebar: String,
        @Field("total_harga") total_harga: String,
        @Field("gambar") gambar: String,
        @Field("waktu") waktu: String,
        @Field("waktu_konfirmasi") waktu_konfirmasi: String,
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postDeletePesanan(
        @Field("delete_pesanan") delete_pesanan:String,
        @Field("id_pesanan") id_pesanan: String
    ): ArrayList<ResponseModel>


    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postAdminTambahPesananDetail(
        @Field("admin_tambah_pesanan_detail") admin_tambah_pesanan_detail: String,
        @Field("id_user") id_user: String,
        @Field("nama") nama: String,
        @Field("alamat") alamat: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("id_jenis_plafon") id_jenis_plafon: String,
        @Field("harga") harga: String,
        @Field("panjang") panjang: String,
        @Field("lebar") lebar: String,
        @Field("total_harga") total_harga: String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postAdminUpdatePesananDetail(
        @Field("admin_update_pesanan_detail") admin_update_pesanan_detail: String,
        @Field("id_pesanan") id_pesanan: String,
        @Field("id_pemesanan") id_pemesanan: String,
        @Field("nama") nama: String,
        @Field("alamat") alamat: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("jenis_plafon") jenis_plafon: String,
        @Field("harga") harga: String,
        @Field("panjang") panjang: String,
        @Field("lebar") lebar: String,
        @Field("total_harga") total_harga: String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postAdminDeletePesananDetail(
        @Field("admin_delete_pesanan_detail") admin_delete_pesanan_detail:String,
        @Field("id_pesanan") id_pesanan: String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postAdminUpdateKonrimasiPembayaran(
        @Field("admin_update_konfirmasi_pembayaran") admin_update_konfirmasi_pembayaran: String,
        @Field("id_pemesanan") id_pemesanan: String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postAdminUpdateKonrimasiSelesai(
        @Field("admin_update_konfirmasi_selesai") admin_update_konfirmasi_selesai: String,
        @Field("id_pemesanan") id_pemesanan: String
    ): ArrayList<ResponseModel>


    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postTambahRiwayatPesanan(
        @Field("tambah_pesanan") tambah_pesanan: String,
        @Field("id_pemesanan") id_pemesanan: String,
        @Field("id_user") id_user: String,
        @Field("nama") nama: String,
        @Field("alamat") alamat: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("jenis_plafon") jenis_plafon: String,
        @Field("harga") harga: String,
        @Field("panjang") panjang: String,
        @Field("lebar") lebar: String,
        @Field("total_harga") total_harga: String,
        @Field("waktu") waktu: String,
        @Field("waktu_konfirmasi") waktu_konfirmasi: String,
        @Field("metode_pembayaran") metode_pembayaran: String,
        @Field("waktu_bayar") waktu_bayar: String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postUpdateRiwayatPesanan(
        @Field("update_pesanan") update_pesanan: String,
        @Field("id_pesanan") id_pesanan: String,
        @Field("id_user") id_user: String,
        @Field("nama") nama: String,
        @Field("alamat") alamat: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("jenis_plafon") jenis_plafon: String,
        @Field("harga") harga: String,
        @Field("panjang") panjang: String,
        @Field("lebar") lebar: String,
        @Field("total_harga") total_harga: String,
        @Field("gambar") gambar: String,
        @Field("waktu") waktu: String,
        @Field("waktu_konfirmasi") waktu_konfirmasi: String,
        @Field("metode_pembayaran") metode_pembayaran: String,
        @Field("waktu_bayar") waktu_bayar: String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postDeleteRiwayatPesanan(
        @Field("delete_pesanan") delete_pesanan:String,
        @Field("id_riwayat_pesanan") id_riwayat_pesanan: String
    ): ArrayList<ResponseModel>


    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postTambahRiwayatPesananDetail(
        @Field("admin_tambah_riwayat_pesanan_detail") admin_tambah_riwayat_pesanan_detail: String,
        @Field("id_user") id_user: String,
        @Field("id_pemesanan") id_pemesanan: String,
        @Field("id_plafon") id_plafon: String,
        @Field("nama_lengkap") nama_lengkap: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("kecamatan_kab_kota") kecamatan_kab_kota: String,
        @Field("alamat") alamat: String,
        @Field("detail_alamat") detail_alamat: String,
        @Field("jumlah") jumlah: String,
//        @Field("harga") harga: String,
//        @Field("total_harga") total_harga: String,
        @Field("metode_pembayaran") metode_pembayaran: String,
//        @Field("waktu") waktu: String,
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postUpdateRiwayatPesananDetail(
        @Field("admin_update_riwayat_pesanan_detail") admin_update_riwayat_pesanan_detail: String,
        @Field("id_riwayat_pesanan") id_riwayat_pesanan: String,
        @Field("id_user") id_user: String,
        @Field("id_pemesanan") id_pemesanan: String,
        @Field("id_plafon") id_plafon: String,
        @Field("nama_lengkap") nama_lengkap: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("kecamatan_kab_kota") kecamatan_kab_kota: String,
        @Field("alamat") alamat: String,
        @Field("detail_alamat") detail_alamat: String,
        @Field("jumlah") jumlah: String,
        @Field("metode_pembayaran") metode_pembayaran: String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postDeleteRiwayatPesananDetail(
        @Field("admin_delete_riwayat_pesanan") admin_delete_riwayat_pesanan:String,
        @Field("id_riwayat_pesanan") id_riwayat_pesanan: String
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postTambahTestimoni(
        @Field("tambah_testimoni") tambah_testimoni:String,
        @Field("id_pemesanan") id_pemesanan: Int,
        @Field("id_wo") id_wo: Int,
        @Field("testimoni") testimoni: String,
        @Field("bintang") bintang: String,
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postUpdateTestimoni(
        @Field("update_testimoni") update_testimoni:String,
        @Field("id_testimoni") id_testimoni: String,
        @Field("testimoni") testimoni: String,
        @Field("bintang") bintang: String,
    ): ArrayList<ResponseModel>



}