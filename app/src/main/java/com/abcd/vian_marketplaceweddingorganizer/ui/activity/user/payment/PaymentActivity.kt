package com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.adapter.ListPaymentVendorAdapter
import com.abcd.vian_marketplaceweddingorganizer.data.model.AlamatModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.RekeningModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.VendorModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityPaymentBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.address.ChooseAddressActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.main.MainActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.Constant
import com.abcd.vian_marketplaceweddingorganizer.utils.KataAcak
import com.abcd.vian_marketplaceweddingorganizer.utils.KonversiRupiah
import com.abcd.vian_marketplaceweddingorganizer.utils.LoadingAlertDialog
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin
import com.abcd.vian_marketplaceweddingorganizer.utils.TanggalDanWaktu
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import com.midtrans.sdk.uikit.api.model.CustomerDetails
import com.midtrans.sdk.uikit.api.model.ItemDetails
import com.midtrans.sdk.uikit.api.model.SnapTransactionDetail
import com.midtrans.sdk.uikit.external.UiKitApi
import com.midtrans.sdk.uikit.internal.util.UiKitConstants
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    private val viewModel: PaymentViewModel by viewModels()
    @Inject
    lateinit var tanggalDanWaktu: TanggalDanWaktu
    @Inject
    lateinit var hurufAcak: KataAcak
    @Inject
    lateinit var rupiah: KonversiRupiah
    @Inject
    lateinit var loading: LoadingAlertDialog
    @Inject
    lateinit var kataAcak: KataAcak

    var kodeUnik = ""

    private lateinit var vendor: ArrayList<VendorModel>
    private var listRekening: ArrayList<RekeningModel> = arrayListOf()
    private var listJenisRekening : ArrayList<String> = arrayListOf()
    private var namaWeddingOrganizer = ""
    private var idUser = 0
    private var namaLengkap = ""
    private var nomorHp = ""
    private var kecamatanKabKota = ""
    private var alamat = ""
    private var detailAlamat = ""
    private var jenisPembayaran = ""

    var totalHarga: Int = 0
    var totalHargaKodeUnik: Int = 0

    private var idWo = 0
    private var idVendor = ""
    private var waktuAcara = ""

    private var selectedValue = ""
    private var numberPosition = 0

    private var selectedValueRekening = ""
    private var numberPositionRekening = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchDataSebelumnya()
        setSharedPreferencesLogin()
        setAppNavbarDrawer()
        setButton()
        getRekening()
        fetchAlamat(sharedPreferencesLogin.getIdUser())
        getAlamat()
        getDataRegistrasiPembayaran()
        getTambahPesananDitempat()
        setSpinner()
    }

    private fun setSpinner() {
        setSpinnerMetodePembayaran()
//        setSpinnerJenisRekening()
    }

    private fun fetchDataSebelumnya() {
        val extras = intent.extras
        if(extras != null) {
            vendor = arrayListOf()
            vendor = intent.getParcelableArrayListExtra("vendor")!!
            namaWeddingOrganizer = intent.getStringExtra("nama_wedding_organizer")!!
            idWo = intent.getIntExtra("idWo", 0)

            binding.apply {
                tvTanggalAcara.text = "${tanggalDanWaktu.tanggalSekarangZonaMakassar()} 10:00"
                kodeUnik = kataAcak.getAngkaSaja(4)
                tvKodeUnik.text = kodeUnik.toString()
                totalHargaKodeUnik += kodeUnik.toInt()
            }

            fetchRekening(idWo)

            if(vendor.isNotEmpty()){
                setVendor(vendor)
            } else{
                Toast.makeText(this@PaymentActivity, "Vendor kosong", Toast.LENGTH_SHORT).show()
            }
            for(values in vendor){
                idVendor+="${values.id_vendor};;;"
            }
        }
    }

    private fun setAppNavbarDrawer() {
        binding.appNavbarDrawer.apply {
            tvTitle.text = namaWeddingOrganizer
            ivNavDrawer.visibility = View.GONE
            ivBack.visibility = View.VISIBLE
        }
    }
    private fun setSharedPreferencesLogin() {
        sharedPreferencesLogin = SharedPreferencesLogin(this@PaymentActivity)
        idUser = sharedPreferencesLogin.getIdUser()
    }

    private fun setButton() {
        binding.apply {
            binding.appNavbarDrawer.ivBack.setOnClickListener {
//                startActivity(Intent(this@PaymentActivity, MainActivity::class.java))
                finish()
            }
            btnBayar.setOnClickListener {
                if(tvAlamat.text.toString().trim() == resources.getString(R.string.alamat).trim()){
                    Toast.makeText(this@PaymentActivity, "Tolong masukkan alamat anda", Toast.LENGTH_SHORT).show()
                } else if(tvTanggalAcara.text == resources.getString(R.string.alamat).trim()){
                    Toast.makeText(this@PaymentActivity, "Tolong masukkan Tanggal Acara", Toast.LENGTH_SHORT).show()
                } else{
                    if(numberPosition == 0){
//                        fetchDataRegistrasiPembayaran(uuid, idUser)
                        postDataRegistrasiPembayaran(idUser, idWo, idVendor, kodeUnik, tanggalDanWaktu.tanggalDanWaktuZonaMakassar(), tvTanggalAcara.text.toString().trim())
                    } else{
                        postTambahPesananDitempat(idUser, idWo, idVendor, tanggalDanWaktu.tanggalDanWaktuZonaMakassar(), tvTanggalAcara.text.toString().trim())
                    }
                }
            }

            clAlamat.setOnClickListener {
                val i = Intent(this@PaymentActivity, ChooseAddressActivity::class.java)
                i.putParcelableArrayListExtra("vendor", vendor)
                i.putExtra("nama_wedding_organizer", namaWeddingOrganizer)
                i.putExtra("idWo", idWo)
                startActivity(i)
                finish()
            }

            tvTanggalAcara.setOnClickListener{
                catchTanggalDanWaktu()
            }
        }
    }

    private fun setSpinnerMetodePembayaran(){
        binding.apply {
            // Spinner Metode Pembayaran
            val arrayAdapter = ArrayAdapter.createFromResource(
                this@PaymentActivity,
                R.array.metode_pembayaran,
                android.R.layout.simple_spinner_item
            )
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spMetodePembayaran.adapter = arrayAdapter

            spMetodePembayaran.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    numberPosition = spMetodePembayaran.selectedItemPosition
                    selectedValue = spMetodePembayaran.selectedItem.toString()

                    if(position==0){
//                        fetchRekening(idWo)
//                        clJenisRekening.visibility = View.VISIBLE
                        clKodeUnik.visibility = View.VISIBLE

                        tvTotalTagihan.text = rupiah.rupiah(totalHargaKodeUnik.toLong())
                    } else{
//                        clJenisRekening.visibility = View.GONE
                        clKodeUnik.visibility = View.GONE

                        tvTotalTagihan.text = rupiah.rupiah(totalHarga.toLong())
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
            spMetodePembayaran.adapter = arrayAdapter
        }
    }

    private fun setSpinnerJenisRekening(){
        binding.apply {
            // Spinner Metode Pembayaran
            val arrayAdapter = ArrayAdapter(
                this@PaymentActivity,
                android.R.layout.simple_spinner_item,
                listJenisRekening
            )
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spJenisRekening.adapter = arrayAdapter

            spJenisRekening.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    numberPositionRekening = spJenisRekening.selectedItemPosition
                    selectedValueRekening = spJenisRekening.selectedItem.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
            spJenisRekening.adapter = arrayAdapter
        }
    }

    private fun fetchRekening(idWo: Int){
        viewModel.fetchRekening(idWo)
    }

    private fun getRekening(){
        viewModel.getRekening().observe(this@PaymentActivity){result->
            when(result){
                is UIState.Loading-> {}
                is UIState.Success->setSuccessFetchRekening(result.data)
                is UIState.Failure->setFailureFetchRekening(result.message)
                else -> {}
            }
        }
    }

    private fun setFailureFetchRekening(message: String) {
//        Toast.makeText(this@PaymentActivity, "rekening: $message", Toast.LENGTH_SHORT).show()
//        setStopShimmer()
    }

    private fun setSuccessFetchRekening(data: ArrayList<RekeningModel>) {
        if(data.isNotEmpty()){
            listRekening = data
            listJenisRekening = data.map { it.jenis_rekening!! } as ArrayList<String>

            setSpinnerJenisRekening()
        }
    }

    private fun fetchAlamat(idUser: Int){
        viewModel.fetchAlamat(idUser)
    }

    private fun getAlamat(){
        viewModel.getAlamat().observe(this@PaymentActivity){result->
            when(result){
                is UIState.Loading->setStartShimmer()
                is UIState.Success->setSuccessFetchAlamat(result.data)
                is UIState.Failure->setFailureFetchAlamat(result.message)
                else -> {}
            }
        }
    }

    private fun setFailureFetchAlamat(message: String) {
        Toast.makeText(this@PaymentActivity, "alamat: $message", Toast.LENGTH_SHORT).show()
        setStopShimmer()
    }

    private fun setSuccessFetchAlamat(data: ArrayList<AlamatModel>) {
        setAlamat(data)
        setStopShimmer()
    }

    private fun setVendor(data: ArrayList<VendorModel>) {
        Log.d("PaymentActivityTAG", "setDataSuccessPembayaran: ${data.size}")
        if(data.size>0){
            Log.d("MainActivityTag", "setData: $data")

            var no = 1

            for (value in data){
                val harga = value.harga!!
                val namaVendor = value.nama_vendor
//                val panjang = value.panjang

                totalHarga += harga
                totalHargaKodeUnik += harga
                Log.d("PaymentActivityTAG", "set: no: $no, " +
                        "harga: $totalHarga, namaVendor: $namaVendor ")

                no++
            }

            setAdapter(data)
            binding.tvTotalTagihan.text = rupiah.rupiah(totalHargaKodeUnik.toLong())

        } else{
            Toast.makeText(this@PaymentActivity, "Terima Kasih Telah Memesan", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@PaymentActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun setAlamat(data: ArrayList<AlamatModel>) {
        binding.apply {
            // Alamat
            var alamatModel = AlamatModel("", "", "", "", "", "", "")
            if(data.isNotEmpty()){
                alamatModel = data[0]
//                for(values in data){
//                    if(values.main == "1"){
//                        alamatModel = values
//                    }
//                }
            } else{
                // Tidak ada data
                tvNama.text = sharedPreferencesLogin.getNama()
                tvNomorHp.text = sharedPreferencesLogin.getNomorHp()
                tvAlamat.text = resources.getString(R.string.alamat)
            }
            if(alamatModel.id_alamat!!.isNotEmpty()){
                val kecamatan = "${alamatModel.kab_kota!!.listKecamatan!![0].kecamatan}, ${alamatModel.kab_kota!!.kab_kota}"
                kecamatanKabKota = kecamatan
                tvNama.text = alamatModel.nama_lengkap
                tvNomorHp.text = alamatModel.nomor_hp
                tvKecamatan.text = kecamatan
                tvAlamat.text = alamatModel.alamat
                tvAlamatDetail.text = alamatModel.detail_alamat
            } else{
                tvNama.text = sharedPreferencesLogin.getNama()
                tvNomorHp.text = sharedPreferencesLogin.getNomorHp()
                tvAlamat.text = resources.getString(R.string.alamat)
            }

            namaLengkap = alamatModel.nama_lengkap!!
            nomorHp = alamatModel.nomor_hp!!
            alamat = alamatModel.alamat!!
            detailAlamat = alamatModel.detail_alamat!!

        }
    }

    private fun setAdapter(data: ArrayList<VendorModel>) {
        binding.apply {
            val adapter = ListPaymentVendorAdapter(data)
            rvPesanan.layoutManager = LinearLayoutManager(this@PaymentActivity, LinearLayoutManager.VERTICAL, false)
            rvPesanan.adapter = adapter
        }
    }

    private fun postDataRegistrasiPembayaran(
        idUser: Int,
        idWo: Int,
        idVendor: String,
        kodeUnik: String,
        waktu: String,
        waktuAcara: String,
    ) {
        viewModel.postRegistrasiPembayaran(idUser, idWo, idVendor, kodeUnik, waktu, waktuAcara)
    }

    private fun getDataRegistrasiPembayaran() {
        viewModel.getRegistrasiPembayaran().observe(this@PaymentActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@PaymentActivity)
                is UIState.Success-> setDataSuccessRegistrasiPembayaran(result.data)
                is UIState.Failure-> setDataFailureRegistrasiPembayaran(result.message)
                else->{}
            }
        }
    }

    private fun setDataFailureRegistrasiPembayaran(message: String) {
        loading.alertDialogCancel()
        Toast.makeText(this@PaymentActivity, "Ada masalah : $message", Toast.LENGTH_SHORT).show()
    }

    private fun setDataSuccessRegistrasiPembayaran(data: ArrayList<ResponseModel>) {
        if(data.isNotEmpty()){
            if(data[0].status == "0"){
                Toast.makeText(this@PaymentActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                finish()
            } else{
                Toast.makeText(this@PaymentActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        }
        loading.alertDialogCancel()
    }

    private fun postTambahPesananDitempat(
        idUser: Int,
        idWo: Int,
        idVendor: String,
        waktu: String,
        waktuAcara: String,
    ) {
        viewModel.postPesanInPlace(idUser, idWo, idVendor, waktu, waktuAcara)
    }
    private fun getTambahPesananDitempat(){
        viewModel.getPostPesan().observe(this@PaymentActivity){result->
            when(result){
                is UIState.Loading -> loading.alertDialogLoading(this@PaymentActivity)
                is UIState.Failure -> setFailurePostPesan(result.message)
                is UIState.Success -> setSuccessPostPesan(result.data)
                else->{}
            }
        }
    }

    private fun setFailurePostPesan(message: String) {
        loading.alertDialogCancel()
        Toast.makeText(this@PaymentActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessPostPesan(data: ArrayList<ResponseModel>) {
        loading.alertDialogCancel()
        if(data.isNotEmpty()){
            if(data[0].status=="0"){
                Toast.makeText(this@PaymentActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@PaymentActivity, MainActivity::class.java))
                finish()
            } else{
                Toast.makeText(this@PaymentActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@PaymentActivity, "No respon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun catchTanggalDanWaktu(){
        tanggalDanWaktu.selectedDateTime(
            tanggalDanWaktu.tanggalSekarangZonaMakassar(),
            binding.tvTanggalAcara,
            this@PaymentActivity
        )
    }

    private fun setStartShimmer(){
        binding.apply {
            smAlamat.startShimmer()
            smAlamat.visibility = View.VISIBLE

            clAlamatDetail.visibility = View.GONE
        }
    }

    private fun setStopShimmer(){
        binding.apply {
            smAlamat.stopShimmer()
            clAlamatDetail.visibility = View.VISIBLE

            smAlamat.visibility = View.GONE

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        startActivity(Intent(this@PaymentActivity, MainActivity::class.java))
        finish()
    }
}