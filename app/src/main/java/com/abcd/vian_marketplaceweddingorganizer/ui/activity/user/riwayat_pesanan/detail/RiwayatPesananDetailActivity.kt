package com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.riwayat_pesanan.detail

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.OpenableColumns
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.adapter.ListJenisRekeningAdapter
import com.abcd.vian_marketplaceweddingorganizer.adapter.RiwayatPesananDetailAdapter
import com.abcd.vian_marketplaceweddingorganizer.data.model.RekeningModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.RiwayatPesananModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.TestimoniModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.UsersModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityRiwayatPesananDetailBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogShowImageBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogTestimoniTambahBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.register.RegisterActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.Constant
import com.abcd.vian_marketplaceweddingorganizer.utils.KonversiRupiah
import com.abcd.vian_marketplaceweddingorganizer.utils.LoadingAlertDialog
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.TanggalDanWaktu
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@AndroidEntryPoint
class RiwayatPesananDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRiwayatPesananDetailBinding
    private val viewModel: RiwayatPesananDetailViewModel by viewModels()
    private var idPemesanan = 1
    private var idWo = 1
    @Inject
    lateinit var tanggalDanWaktu: TanggalDanWaktu
    @Inject
    lateinit var rupiah: KonversiRupiah
    @Inject
    lateinit var loading: LoadingAlertDialog
    private var imageBuktiPembayaran: String? = null
    private var tempUser: UsersModel = UsersModel()
    private var fileImage: MultipartBody.Part? = null
    var namaWo = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatPesananDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButton()
        setDataSebelumnya()
        getRiwayatPembayaran()
        getTestimoni()
        getRekening()
        getTambahTestimoni()
        getUpdateTestimoni()
        getUploadBuktiPembayaran()
    }

    private fun setButton() {
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
            btnTestimoni.setOnClickListener{
                loading.alertDialogLoading(this@RiwayatPesananDetailActivity)
                fetchTestimoni(idPemesanan)
            }
            btnKonfirmasiPesanan.setOnClickListener{

            }
            btnUpload.setOnClickListener {
                if(fileImage != null){
                    postUploadBuktiPembayaran()
                } else{
                    binding.btnBuktiPembayaran.error = "Masukkan gambar"
                }
            }
        }
        setBuktiPembayaran()
    }

    private fun postUploadBuktiPembayaran() {
        val vPost = convertStringToMultipartBody("post_bukti_pembayaran")
        val vIdPemesanan = convertStringToMultipartBody(idPemesanan.toString())

        viewModel.postBuktiPembayaran(vPost, vIdPemesanan, fileImage!!)
    }

    private fun getUploadBuktiPembayaran(){
        viewModel.getResponseBuktiPembayaran().observe(this@RiwayatPesananDetailActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@RiwayatPesananDetailActivity)
                is UIState.Success-> setSuccessPostBuktiPembayaran(result.data)
                is UIState.Failure-> setFailurePostBuktiPembayaran(result.message)
            }
        }
    }

    private fun setSuccessPostBuktiPembayaran(data: java.util.ArrayList<ResponseModel>) {
        if(data.isNotEmpty()){
            if(data[0].status == "0"){
                fetchRekening(idWo)
                fetchRiwayatPembayaran(idPemesanan)

                fileImage = null
                binding.btnBuktiPembayaran.text = resources.getString(R.string.ket_klik_file)
            } else{
                Toast.makeText(this@RiwayatPesananDetailActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        }
        loading.alertDialogCancel()
    }

    private fun setFailurePostBuktiPembayaran(message: String) {
        Toast.makeText(this@RiwayatPesananDetailActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setBuktiPembayaran() {
        setButtonUploadBukti()
        setButtonLihatBukti()
    }

    private fun setButtonUploadBukti() {
        binding.btnBuktiPembayaran.setOnClickListener {
            if(checkPermission()){
                pickImageFile()
            } else{
                requestPermission()
            }
        }
    }

    private fun setButtonLihatBukti() {
        binding.ivBuktiPembayaran.setOnClickListener {
            setShowImage(imageBuktiPembayaran!!,"Bukti Pembayaran")
        }
    }

    private fun setDataSebelumnya() {
        val i = intent.extras
        if(i != null){
            idPemesanan = i.getInt("idPemesanan")!!
            fetchRiwayatPembayaran(idPemesanan)
        }
    }

    private fun fetchRiwayatPembayaran(idPemesanan: Int) {
        viewModel.fetchDetailRiwayatPesanan(idPemesanan)
    }
    private fun getRiwayatPembayaran(){
        viewModel.getDetailRiwayatPesanan().observe(this@RiwayatPesananDetailActivity){ result->
            when(result){
                is UIState.Loading->{}
                is UIState.Success-> setSuccessFetchRiwayatPembayaran(result.data)
                is UIState.Failure-> setFailureFetchRiwayatPembayaran(result.message)
            }
        }
    }

    private fun setFailureFetchRiwayatPembayaran(message: String) {
        Toast.makeText(this@RiwayatPesananDetailActivity, message, Toast.LENGTH_SHORT).show()
        Log.d("DetailTAG", "setFailureFetchRiwayatPembayaran: $message")
    }

    private fun setSuccessFetchRiwayatPembayaran(data: ArrayList<RiwayatPesananModel>) {
        if(data.isNotEmpty()){
            setAlamat(data)
            setKeterangan(data)
            setAdapter(data)

            imageBuktiPembayaran = data[0].bukti_pembayaran

            if(data[0].bukti_pembayaran!!.isEmpty() || data[0].bukti_pembayaran == null){
                binding.ivBuktiPembayaran.visibility = View.GONE
            } else{
                binding.ivBuktiPembayaran.visibility = View.VISIBLE
                imageBuktiPembayaran = data[0].bukti_pembayaran
            }
        } else{
            Toast.makeText(this@RiwayatPesananDetailActivity, "Tidak ada data Jenis Plafon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAlamat(data: ArrayList<RiwayatPesananModel>) {
        binding.apply {
            tvNama.text = data[0].nama_lengkap
            tvNomorHp.text = data[0].nomor_hp
            tvKecamatan.text = "Kecamatan ${data[0].kecamatan_kab_kota}"
            tvAlamat.text = data[0].alamat
            tvAlamatDetail.text = data[0].detail_alamat
        }
    }

    private fun setKeterangan(data: ArrayList<RiwayatPesananModel>) {
        var totalHarga = 0
        for (values in data){
            totalHarga += values.harga!!
        }
        idWo = data[0].wo!!.id_wo!!
        var array = data[0].waktu_acara!!.split(" ")
        var tanggalAcara = ""
        var tanggalPesanan = ""
        var tanggalBayar = ""
        if(data[0].waktu_acara!!.isNotEmpty()){
            array = data[0].waktu_acara!!.split(" ")
            tanggalAcara = "${tanggalDanWaktu.konversiBulanSingkatan(array[0])} ${tanggalDanWaktu.waktuNoSecond(array[1])}"
        }
        if(data[0].waktu!!.isNotEmpty()){
            array = data[0].waktu!!.split(" ")
            tanggalPesanan = "${tanggalDanWaktu.konversiBulanSingkatan(array[0])} ${tanggalDanWaktu.waktuNoSecond(array[1])}"
        }
        if(data[0].waktu_bayar!!.isNotEmpty()){
            array = data[0].waktu_bayar!!.split(" ")
            tanggalBayar = "${tanggalDanWaktu.konversiBulanSingkatan(array[0])} ${tanggalDanWaktu.waktuNoSecond(array[1])}"
        }
        var selesai = data[0].selesai!!
        if(selesai == "0"){
            selesai = "On Progress"
            binding.btnTestimoni.visibility = View.GONE
            binding.btnKonfirmasiPesanan.visibility = View.VISIBLE
        } else{
            selesai = "Pesanan Telah Selesai"
            binding.btnTestimoni.visibility = View.VISIBLE
            binding.btnKonfirmasiPesanan.visibility = View.GONE
        }

        val bayar = data[0].ket!!
        if(bayar == "0"){
            fetchRekening(idWo)
            binding.btnKonfirmasiPesanan.visibility = View.GONE
        } else{
            binding.btnKonfirmasiPesanan.visibility = View.VISIBLE
        }

        // Pembayaran Online
        val metodePembayaran = data[0].metode_pembayaran!!
        if(metodePembayaran == "Online"){
            binding.clPembayaran.visibility = View.VISIBLE
        } else{
            binding.clPembayaran.visibility = View.GONE
        }

        namaWo = data[0].wo!!.nama!!
        binding.tvWO.text = namaWo
        binding.tvTitlePembayaran.text = "Daftar Bank $namaWo"

        binding.apply {
            tvTotalHarga.text = rupiah.rupiah(totalHarga.toLong())
            tvMetodePembayaran.text = metodePembayaran
            tvTanggalAcara.text = tanggalAcara
            tvTanggalPesanan.text = tanggalPesanan
            tvTanggalBayar.text = tanggalBayar
            tvKet.text = selesai
        }
    }

    private fun setAdapter(data: java.util.ArrayList<RiwayatPesananModel>) {
        val adapter = RiwayatPesananDetailAdapter(data)
        binding.apply {
            rvDetailRiwayatPesanan.layoutManager = LinearLayoutManager(this@RiwayatPesananDetailActivity, LinearLayoutManager.VERTICAL, false)
            rvDetailRiwayatPesanan.adapter = adapter
        }
    }

    private fun fetchRekening(idWo: Int) {
        viewModel.fetchRekening(idWo)
    }
    private fun getRekening(){
        viewModel.getRekening().observe(this@RiwayatPesananDetailActivity){ result->
            when(result){
                is UIState.Loading->{}
                is UIState.Success-> setSuccessFetchJenisRekening(result.data)
                is UIState.Failure-> setFailureFetchJenisRekening(result.message)
            }
        }
    }

    private fun setFailureFetchJenisRekening(message: String) {
        Toast.makeText(this@RiwayatPesananDetailActivity, message, Toast.LENGTH_SHORT).show()
        Log.d("DetailTAG", "setFailureFetchJenisRekening: $message")
    }

    private fun setSuccessFetchJenisRekening(data: ArrayList<RekeningModel>) {
        if(data.isNotEmpty()){
            setAdapterRekening(data)
        } else{
            Toast.makeText(this@RiwayatPesananDetailActivity, "Tidak ada Rekening", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdapterRekening(data: ArrayList<RekeningModel>) {
        val adapter = ListJenisRekeningAdapter(data)
        binding.apply {
            rvJenisRekening.layoutManager = LinearLayoutManager(this@RiwayatPesananDetailActivity, LinearLayoutManager.VERTICAL, false)
            rvJenisRekening.adapter = adapter
        }
    }

    private fun fetchTestimoni(idPemesanan: Int) {
        viewModel.fetchTestimoni(idPemesanan)
    }

    private fun getTestimoni(){
        viewModel.getTestimoni().observe(this@RiwayatPesananDetailActivity){result->
            when(result){
                is UIState.Loading->{}
                is UIState.Failure-> setFailureFetchTestimoni(result.message)
                is UIState.Success-> setSuccessFetchTestimoni(result.data)
            }
        }
    }

    private fun setFailureFetchTestimoni(message: String) {
        loading.alertDialogCancel()
        Toast.makeText(this@RiwayatPesananDetailActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessFetchTestimoni(data: ArrayList<TestimoniModel>) {
        if(data.isNotEmpty()){
            loading.alertDialogCancel()
//            setDataTestimoni(data)
            showTestimoni(data)
        } else{
            val listKosong = arrayListOf<TestimoniModel>()
            loading.alertDialogCancel()
            showTestimoni(listKosong)
        }
    }

    private fun showTestimoni(data: ArrayList<TestimoniModel>) {
        val view = AlertDialogTestimoniTambahBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(this@RiwayatPesananDetailActivity)
        alertDialog.setView(view.root)
            .setCancelable(false)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            //set data
            var idTestimoni = ""
            var jumlahBintang = 0
//            tvJenisPlafon.text = jenisPlafon
            if(data.isNotEmpty()){
                jumlahBintang = data[0].bintang!!.trim().toInt()
                idTestimoni = data[0].id_testimoni!!
                val komentar = data[0].testimoni!!
                val bintang = data[0].bintang!!.trim().toInt()

                etTestimoni.setText(komentar)

                when (bintang) {
                    1 -> {
                        ivPostBintang1.setImageResource(R.drawable.icon_star_aktif)
                    }
                    2 -> {
                        ivPostBintang1.setImageResource(R.drawable.icon_star_aktif)
                        ivPostBintang2.setImageResource(R.drawable.icon_star_aktif)
                    }
                    3 -> {
                        ivPostBintang1.setImageResource(R.drawable.icon_star_aktif)
                        ivPostBintang2.setImageResource(R.drawable.icon_star_aktif)
                        ivPostBintang3.setImageResource(R.drawable.icon_star_aktif)
                    }
                    4 -> {
                        ivPostBintang1.setImageResource(R.drawable.icon_star_aktif)
                        ivPostBintang2.setImageResource(R.drawable.icon_star_aktif)
                        ivPostBintang3.setImageResource(R.drawable.icon_star_aktif)
                        ivPostBintang4.setImageResource(R.drawable.icon_star_aktif)
                    }
                    5 -> {
                        ivPostBintang1.setImageResource(R.drawable.icon_star_aktif)
                        ivPostBintang2.setImageResource(R.drawable.icon_star_aktif)
                        ivPostBintang3.setImageResource(R.drawable.icon_star_aktif)
                        ivPostBintang4.setImageResource(R.drawable.icon_star_aktif)
                        ivPostBintang5.setImageResource(R.drawable.icon_star_aktif)
                    }
                }
            } else{
                idTestimoni = ""
            }

            ivPostBintang1.setOnClickListener {
                setBintang1(ivPostBintang1, ivPostBintang2, ivPostBintang3, ivPostBintang4, ivPostBintang5)
                jumlahBintang = 1
            }
            ivPostBintang2.setOnClickListener {
                setBintang2(ivPostBintang1, ivPostBintang2, ivPostBintang3, ivPostBintang4, ivPostBintang5)
                jumlahBintang = 2
            }
            ivPostBintang3.setOnClickListener {
                setBintang3(ivPostBintang1, ivPostBintang2, ivPostBintang3, ivPostBintang4, ivPostBintang5)
                jumlahBintang = 3
            }
            ivPostBintang4.setOnClickListener {
                setBintang4(ivPostBintang1, ivPostBintang2, ivPostBintang3, ivPostBintang4, ivPostBintang5)
                jumlahBintang = 4
            }
            ivPostBintang5.setOnClickListener {
                setBintang5(ivPostBintang1, ivPostBintang2, ivPostBintang3, ivPostBintang4, ivPostBintang5)
                jumlahBintang = 5
            }

            btnSimpan.setOnClickListener {
                if(jumlahBintang==0){
                    Toast.makeText(this@RiwayatPesananDetailActivity, "Masukkan Jumlah Bintang", Toast.LENGTH_SHORT).show()
                } else{
                    var check = true
                    if(etTestimoni.text.isEmpty()){
                        etTestimoni.error = "Masukkan Testimoni"
                        check = false
                    }

                    if(check){
                        dialogInputan.dismiss()
                        val testimoni = etTestimoni.text.toString()
                        val bintang = jumlahBintang.toString()

                        if(idTestimoni.isEmpty()){
                            postTambahTestimoni(
                                idPemesanan, idWo, testimoni, bintang
                            )
                        } else{
                            postUpdateTestimoni(idTestimoni, testimoni, bintang)
                        }
                    }

                }
            }
            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun setBintang1(
        iv1: ImageView,
        iv2: ImageView,
        iv3: ImageView,
        iv4: ImageView,
        iv5: ImageView,
    ){
        iv1.setImageResource(R.drawable.icon_star_aktif)
        iv2.setImageResource(R.drawable.icon_star_non_aktif)
        iv3.setImageResource(R.drawable.icon_star_non_aktif)
        iv4.setImageResource(R.drawable.icon_star_non_aktif)
        iv5.setImageResource(R.drawable.icon_star_non_aktif)
    }
    private fun setBintang2(
        iv1: ImageView,
        iv2: ImageView,
        iv3: ImageView,
        iv4: ImageView,
        iv5: ImageView,
    ){
        iv1.setImageResource(R.drawable.icon_star_aktif)
        iv2.setImageResource(R.drawable.icon_star_aktif)
        iv3.setImageResource(R.drawable.icon_star_non_aktif)
        iv4.setImageResource(R.drawable.icon_star_non_aktif)
        iv5.setImageResource(R.drawable.icon_star_non_aktif)
    }
    private fun setBintang3(
        iv1: ImageView,
        iv2: ImageView,
        iv3: ImageView,
        iv4: ImageView,
        iv5: ImageView,
    ){
        iv1.setImageResource(R.drawable.icon_star_aktif)
        iv2.setImageResource(R.drawable.icon_star_aktif)
        iv3.setImageResource(R.drawable.icon_star_aktif)
        iv4.setImageResource(R.drawable.icon_star_non_aktif)
        iv5.setImageResource(R.drawable.icon_star_non_aktif)
    }
    private fun setBintang4(
        iv1: ImageView,
        iv2: ImageView,
        iv3: ImageView,
        iv4: ImageView,
        iv5: ImageView,
    ){
        iv1.setImageResource(R.drawable.icon_star_aktif)
        iv2.setImageResource(R.drawable.icon_star_aktif)
        iv3.setImageResource(R.drawable.icon_star_aktif)
        iv4.setImageResource(R.drawable.icon_star_aktif)
        iv5.setImageResource(R.drawable.icon_star_non_aktif)
    }
    private fun setBintang5(
        iv1: ImageView,
        iv2: ImageView,
        iv3: ImageView,
        iv4: ImageView,
        iv5: ImageView,
    ){
        iv1.setImageResource(R.drawable.icon_star_aktif)
        iv2.setImageResource(R.drawable.icon_star_aktif)
        iv3.setImageResource(R.drawable.icon_star_aktif)
        iv4.setImageResource(R.drawable.icon_star_aktif)
        iv5.setImageResource(R.drawable.icon_star_aktif)
    }

    private fun postTambahTestimoni(idPemesanan: Int, idWo: Int, testimoni: String, bintang: String) {
        viewModel.postTambahData(idPemesanan, idWo, testimoni, bintang)
    }

    private fun getTambahTestimoni(){
        viewModel.getResponseTambahTestimoni().observe(this@RiwayatPesananDetailActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@RiwayatPesananDetailActivity)
                is UIState.Failure-> setFailureTambahTestimoni(result.message)
                is UIState.Success-> setSuccessTambahTestimoni(result.data)
            }
        }
    }

    private fun setFailureTambahTestimoni(message: String) {
        loading.alertDialogCancel()
        Toast.makeText(this@RiwayatPesananDetailActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessTambahTestimoni(data: java.util.ArrayList<ResponseModel>) {
        loading.alertDialogCancel()
        if(data.isNotEmpty()){
            if(data[0].status == "0"){
                Toast.makeText(this@RiwayatPesananDetailActivity, "Berhasil Tambah", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this@RiwayatPesananDetailActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun postUpdateTestimoni(idTestimoni: String, testimoni: String, bintang: String){
        viewModel.postUpdatehData(idTestimoni, testimoni, bintang)
    }

    private fun getUpdateTestimoni(){
        viewModel.getResponseUpdateTestimoni().observe(this@RiwayatPesananDetailActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@RiwayatPesananDetailActivity)
                is UIState.Failure-> setFailureUpdateTestimoni(result.message)
                is UIState.Success-> setSuccessUpdateTestimoni(result.data)
            }
        }
    }

    private fun setFailureUpdateTestimoni(message: String) {
        loading.alertDialogCancel()
        Toast.makeText(this@RiwayatPesananDetailActivity, "Gagal", Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessUpdateTestimoni(data: ArrayList<ResponseModel>) {
        loading.alertDialogCancel()
        if(data.isNotEmpty()){
            if(data[0].status=="0"){
                Toast.makeText(this@RiwayatPesananDetailActivity, "Berhasil", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this@RiwayatPesananDetailActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@RiwayatPesananDetailActivity, "", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setShowImage(gambar: String, jenisPlafon: String) {
        val view = AlertDialogShowImageBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@RiwayatPesananDetailActivity)
        alertDialog.setView(view.root)
            .setCancelable(false)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            tvTitle.text = jenisPlafon
            btnClose.setOnClickListener {
                dialogInputan.dismiss()
            }
        }

        Glide.with(this@RiwayatPesananDetailActivity)
            .load("${Constant.BASE_URL}${Constant.LOCATION_GAMBAR}/$gambar") // URL Gambar
            .error(R.drawable.background_main2)
            .into(view.ivShowImage) // imageView mana yang akan diterapkan

    }

    private fun checkPermission(): Boolean{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            //Android is 11(R) or above
            Environment.isExternalStorageManager()
        }
        else{
            //Android is below 11(R)
            val write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun getNameFile(uri: Uri): String {
        val cursor = contentResolver.query(uri, null, null, null, null)
        val nameIndex = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor?.moveToFirst()
        val name = cursor?.getString(nameIndex!!)
        cursor?.close()
        return name!!
    }

    @SuppressLint("Recycle")
    private fun uploadImageToStorage(pdfUri: Uri?, pdfFileName: String, nameAPI:String): MultipartBody.Part? {
        var pdfPart : MultipartBody.Part? = null
        pdfUri?.let {
            val file = contentResolver.openInputStream(pdfUri)?.readBytes()

            if (file != null) {
                pdfPart = convertFileToMultipartBody(file, pdfFileName, nameAPI)
            }
        }
        return pdfPart
    }

    private fun convertFileToMultipartBody(file: ByteArray, pdfFileName: String, nameAPI:String): MultipartBody.Part?{
//        val requestFile = file.toRequestBody("application/pdf".toMediaTypeOrNull())
        val requestFile = file.toRequestBody("application/pdf".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData(nameAPI, pdfFileName, requestFile)

        return filePart
    }

    private fun requestPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            if (Environment.isExternalStorageManager()) {
                startActivity(Intent(this, RegisterActivity::class.java))
            } else { //request for the permission
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
        } else{
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                Constant.STORAGE_PERMISSION_CODE
            )
        }
    }

    private fun pickImageFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "image/*"
        }

        startActivityForResult(intent, Constant.STORAGE_PERMISSION_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constant.STORAGE_PERMISSION_CODE && resultCode == Activity.RESULT_OK && data != null) {
            // Mendapatkan URI file PDF yang dipilih
            val fileUri = data.data!!

            val nameImage = getNameFile(fileUri)

            binding.btnBuktiPembayaran.text = nameImage

            // Mengirim file PDF ke website menggunakan Retrofit
            fileImage = uploadImageToStorage(fileUri, nameImage, "gambar")
        }
    }

    private fun convertStringToMultipartBody(data: String): RequestBody {
        return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), data)
    }
}