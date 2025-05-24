package com.abcd.vian_marketplaceweddingorganizer.data.database.api

import com.abcd.vian_marketplaceweddingorganizer.data.model.AlamatModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.KabKotaModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.MessageModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.PaketModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.PaketVendorModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.RiwayatPesananListModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.RiwayatPesananModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.TestimoniModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.UsersModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.VendorModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.WeddingOrganizerModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        @Query("id_pengirim") id_pengirim: Int,
    ): ArrayList<MessageModel>

    @GET("marketplace-wo/api/get.php")
    suspend fun getChatListWoWeddingOrganizer(
        @Query("get_list_chat_wo") get_list_chat_wo: String,
        @Query("id_pengirim") id_pengirim: Int,
    ): ArrayList<MessageModel>

    @GET("marketplace-wo/api/get.php")
    suspend fun getChatWeddingOrganizer(
        @Query("get_chat_wedding_organizer") get_chat_wedding_organizer: String,
        @Query("id_pengirim") id_pengirim: Int,
        @Query("id_penerima") id_penerima: Int,
    ): ArrayList<MessageModel>

    @GET("marketplace-wo/api/get.php")
    suspend fun getPaketVendor(
        @Query("get_paket_vendor") get_paket_vendor: String,
        @Query("id_wo") id_wo: Int
    ): ArrayList<PaketModel>

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

    // Wedding organizer
    // Vendor
    @GET("marketplace-wo/api/get.php")
    suspend fun getWeddingOrganizerVendor(
        @Query("get_wo_vendor") get_wo_vendor: String,
        @Query("id_wo") id_wo: Int
    ): ArrayList<VendorModel>

    // Pesanan
    @GET("marketplace-wo/api/get.php")
    suspend fun getWeddingOrganizerPesananList(
        @Query("get_wo_pesanan") get_wo_pesanan: String,
        @Query("id_wo") id_wo: Int,
    ): ArrayList<RiwayatPesananListModel>

    @GET("marketplace-wo/api/get.php")
    suspend fun getWeddingOrganizerRiwayatPesananList(
        @Query("get_wo_riwayat_pesanan") get_wo_riwayat_pesanan: String,
        @Query("id_wo") id_wo: Int,
    ): ArrayList<RiwayatPesananListModel>

    @GET("marketplace-wo/api/get.php")
    suspend fun getWeddingOrganizerRiwayatPesananDetail(
        @Query("get_wo_detail_riwayat_pesanan") get_wo_detail_riwayat_pesanan: String,
        @Query("id_pemesanan") id_pemesanan: Int
    ): ArrayList<RiwayatPesananModel>


    // Admin
    // Akun
    @GET("marketplace-wo/api/get.php")
    suspend fun getAkunWeddingOrganizer(
        @Query("get_admin_akun_wedding_organizer") get_admin_akun_wedding_organizer: String
    ): ArrayList<UsersModel>



    @GET("marketplace-wo/api/get.php")
    suspend fun getAllTestimoni(
        @Query("get_admin_all_testimoni") get_admin_all_testimoni: String,
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

    @Multipart
    @POST("marketplace-wo/api/post.php")
    suspend fun addWo(
        @Part("add_wo") add_wo: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("nomor_hp") nomor_hp: RequestBody,
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody,
        @Part("sebagai") sebagai: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part gambar: MultipartBody.Part,
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
        @Field("id_pengirim") id_pengirim: Int,
        @Field("id_penerima") id_penerima: Int,
        @Field("pengirim") pengirim: String,
    ): ArrayList<ResponseModel>

    @Multipart
    @POST("marketplace-wo/api/post.php")
    suspend fun postMessageImage(
        @Part("post_message_image") post_message_image: RequestBody,
        @Part gambar: MultipartBody.Part,
        @Part("nama_image") nama_image: RequestBody,
        @Part("id_pengirim") id_pengirim: RequestBody,
        @Part("id_penerima") id_penerima: RequestBody,
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
    suspend fun postPesanInPlace(
        @Field("post_pesan_ditempat") post_pesan_ditempat:String,
        @Field("id_user") id_user: Int,
        @Field("id_wo") id_wo: Int,
        @Field("id_vendor") id_vendor: String,
        @Field("waktu") waktu: String,
        @Field("waktu_acara") waktu_acara: String,
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postRegistrasiPembayaran(
        @Field("post_register_pembayaran") post_register_pembayaran:String,
        @Field("kode_unik") kode_unik: String,
        @Field("id_user") id_user: Int,
        @Field("id_wo") id_wo: Int,
        @Field("id_vendor") id_vendor: String,
        @Field("waktu") waktu: String,
        @Field("waktu_acara") waktu_acara: String,
    ): ArrayList<ResponseModel>

    // Wedding Organizer
    // Post vendor
    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postTambahVendor(
        @Field("post_wo_tambah_vendor") post_wo_tambah_vendor: String,
        @Field("id_wo") id_wo: Int,
        @Field("nama_vendor") nama_vendor: String,
        @Field("harga") harga: Int
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postUpdateVendor(
        @Field("post_wo_update_vendor") post_wo_update_vendor: String,
        @Field("id_vendor") id_vendor: Int,
        @Field("nama_vendor") nama_vendor: String,
        @Field("harga") harga: Int
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postDeleteVendor(
        @Field("post_wo_delete_vendor") post_wo_delete_vendor:String,
        @Field("id_vendor") id_vendor: Int
    ): ArrayList<ResponseModel>

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postWeddingOrganizerKonfirmasiPembayaranSelesai(
        @Field("post_wo_konfirmasi_pembayaran") post_wo_konfirmasi_pembayaran: String,
        @Field("id_pemesanan") id_pemesanan: Int,
    ): ArrayList<ResponseModel>



    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postUpdate(
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
    suspend fun postUpdateAkunWo(
        @Field("update_akun_wo") update_akun_wo: String,
        @Field("id_user") id_user: Int,
        @Field("id_wo") id_wo: Int,
        @Field("nama") nama: String,
        @Field("alamat") alamat: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("username_lama") username_lama: String,
        @Field("sebagai") sebagai: String,
        @Field("deskripsi_wo") deskripsi_wo: String,
    ): ArrayList<ResponseModel>

    @Multipart
    @POST("marketplace-wo/api/post.php")
    suspend fun postUpdateAkunWoWithImage(
        @Part("update_akun_wo_with_image") update_akun_wo_with_image: RequestBody,
        @Part("id_user") id_user: RequestBody,
        @Part("id_wo") id_wo: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("nomor_hp") nomor_hp: RequestBody,
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody,
        @Part("username_lama") username_lama: RequestBody,
        @Part("sebagai") sebagai: RequestBody,
        @Part("deskripsi_wo") deskripsi: RequestBody,
        @Part gambar: MultipartBody.Part,
    ): ArrayList<ResponseModel>


    // Admin
    // Akun Wedding Organizer
    @Multipart
    @POST("marketplace-wo/api/post.php")
    suspend fun postTambahAkunWoWithImage(
        @Part("tambah_akun_wo_with_image") tambah_akun_wo_with_image: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("nomor_hp") nomor_hp: RequestBody,
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody,
        @Part("sebagai") sebagai: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part gambar: MultipartBody.Part,
    ): ArrayList<ResponseModel>



    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postUpdateAdminAkunWo(
        @Field("update_admin_akun_wo") update_admin_akun_wo: String,
        @Field("id_user") id_user: Int,
        @Field("id_wo") id_wo: Int,
        @Field("nama") nama: String,
        @Field("alamat") alamat: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("username_lama") username_lama: String,
        @Field("sebagai") sebagai: String,
        @Field("deskripsi_wo") deskripsi_wo: String,
    ): ArrayList<ResponseModel>

    @Multipart
    @POST("marketplace-wo/api/post.php")
    suspend fun postUpdateAdminAkunWoWithImage(
        @Part("update_admin_akun_wo_with_image") update_admin_akun_wo_with_image: RequestBody,
        @Part("id_user") id_user: RequestBody,
        @Part("id_wo") id_wo: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("nomor_hp") nomor_hp: RequestBody,
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody,
        @Part("username_lama") username_lama: RequestBody,
        @Part("sebagai") sebagai: RequestBody,
        @Part("deskripsi_wo") deskripsi: RequestBody,
        @Part gambar: MultipartBody.Part,
    ): ArrayList<ResponseModel>

    // User
    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postUpdateAdminAkunUser(
        @Field("update_admin_akun_user") update_admin_akun_user: String,
        @Field("id_user") id_user: Int,
        @Field("nama") nama: String,
        @Field("alamat") alamat: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("username_lama") username_lama: String,
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

    @FormUrlEncoded
    @POST("marketplace-wo/api/post.php")
    suspend fun postDeleteTestimoni(
        @Field("delete_testimoni") delete_testimoni:String,
        @Field("id_testimoni") id_testimoni: String,
    ): ArrayList<ResponseModel>



}