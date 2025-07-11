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
class PaymentActivity2 : AppCompatActivity() {
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
    private var totalBiaya = 0.0
    private var uuid = UUID.randomUUID().toString()
    @Inject
    lateinit var kataAcak: KataAcak

    private var acak = ""

    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var customerDetails: CustomerDetails
    private var itemDetails: ArrayList<ItemDetails> = arrayListOf()
    private lateinit var initTransactionDetails: SnapTransactionDetail

    private lateinit var vendor: ArrayList<VendorModel>
    private var namaWeddingOrganizer = ""
    private var idUser = 0
    private var namaLengkap = ""
    private var nomorHp = ""
    private var kecamatanKabKota = ""
    private var alamat = ""
    private var detailAlamat = ""
    private var jenisPembayaran = ""

    private var idWo = 0
    private var idVendor = ""
    private var waktuAcara = ""

    private var selectedValue = ""
    private var numberPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        acak = kataAcak.getHurufDanAngka()
        setSharedPreferencesLogin()
        fetchDataSebelumnya()
        setAppNavbarDrawer()
        setButton()
        setSpinnerMetodePembayaran()
        fetchAlamat(sharedPreferencesLogin.getIdUser())
        getAlamat()
        konfigurationMidtrans()
        getDataRegistrasiPembayaran()
        getTambahPesananDitempat()
    }

    private fun fetchDataSebelumnya() {
        val extras = intent.extras
        if(extras != null) {
            vendor = arrayListOf()
            vendor = intent.getParcelableArrayListExtra("vendor")!!
            namaWeddingOrganizer = intent.getStringExtra("nama_wedding_organizer")!!
            idWo = intent.getIntExtra("idWo", 0)

            if(vendor.isNotEmpty()){
                setVendor(vendor)
            } else{
                Toast.makeText(this@PaymentActivity2, "Vendor kosong", Toast.LENGTH_SHORT).show()
            }
            for(values in vendor){
                idVendor+="${values.id_vendor};;;"
            }
        }
        binding.tvTanggalAcara.text = "${tanggalDanWaktu.tanggalSekarangZonaMakassar()} 10:00"
    }

    private fun setAppNavbarDrawer() {
        binding.appNavbarDrawer.apply {
            tvTitle.text = namaWeddingOrganizer
            ivNavDrawer.visibility = View.GONE
            ivBack.visibility = View.VISIBLE
        }
    }
    private fun setSharedPreferencesLogin() {
        sharedPreferencesLogin = SharedPreferencesLogin(this@PaymentActivity2)
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
                    Toast.makeText(this@PaymentActivity2, "Tolong masukkan alamat anda", Toast.LENGTH_SHORT).show()
                } else if(tvTanggalAcara.text == resources.getString(R.string.alamat).trim()){
                    Toast.makeText(this@PaymentActivity2, "Tolong masukkan Tanggal Acara", Toast.LENGTH_SHORT).show()
                } else{
                    if(numberPosition == 0){
//                        fetchDataRegistrasiPembayaran(uuid, idUser)
                        postDataRegistrasiPembayaran(acak, idUser, idWo, idVendor, tanggalDanWaktu.tanggalDanWaktuZonaMakassar(), tvTanggalAcara.text.toString().trim())
                    } else{
                        postTambahPesananDitempat(idUser, idWo, idVendor, tanggalDanWaktu.tanggalDanWaktuZonaMakassar(), tvTanggalAcara.text.toString().trim())
                    }
                }
            }

            clAlamat.setOnClickListener {
                val i = Intent(this@PaymentActivity2, ChooseAddressActivity::class.java)
                i.putParcelableArrayListExtra("vendor", vendor)
                i.putExtra("nama_wedding_organizer", namaWeddingOrganizer)
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
                this@PaymentActivity2,
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
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            spMetodePembayaran.adapter = arrayAdapter
        }
    }

    private fun fetchAlamat(idUser: Int){
        viewModel.fetchAlamat(idUser)
    }

    private fun getAlamat(){
        viewModel.getAlamat().observe(this@PaymentActivity2){ result->
            when(result){
                is UIState.Loading->setStartShimmer()
                is UIState.Success->setSuccessFetchAlamat(result.data)
                is UIState.Failure->setFailureFetchAlamat(result.message)
                else -> {}
            }
        }
    }

    private fun setFailureFetchAlamat(message: String) {
        Toast.makeText(this@PaymentActivity2, "alamat: $message", Toast.LENGTH_SHORT).show()
        setStopShimmer()
    }

    private fun setSuccessFetchAlamat(data: ArrayList<AlamatModel>) {
        setAlamat(data)
        setStopShimmer()
    }

    private fun setVendor(data: ArrayList<VendorModel>) {
        Log.d("PaymentActivityTAG", "setDataSuccessPembayaran: ${data.size}")
        if(data.size>0){
            var totalHarga: Double = 0.0

            Log.d("MainActivityTag", "setData: $data")

            var no = 1

            for (value in data){
                val harga = value.harga!!
                val namaVendor = value.nama_vendor
//                val panjang = value.panjang

                totalHarga += harga
                Log.d("PaymentActivityTAG", "set: no: $no, " +
                        "harga: $totalHarga, namaVendor: $namaVendor ")

                itemDetails.add(
                    ItemDetails(
                        "$no", harga.toDouble(), 1, "$namaVendor"
                    )
                )
                no++
            }

            totalBiaya = totalHarga.toDouble()
            Log.d("PaymentActivityTAG", "setDataSuccessPembayaran: $totalBiaya")

            initTransactionDetails = SnapTransactionDetail(
                acak,
                totalBiaya
            )

            binding.apply {
                setAdapter(data)
                tvTotalTagihan.text = rupiah.rupiah(totalHarga.toLong())
            }
        } else{
            Toast.makeText(this@PaymentActivity2, "Terima Kasih Telah Memesan", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@PaymentActivity2, MainActivity::class.java))
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

            setCustomerDetails()
        }
    }

    private fun setAdapter(data: ArrayList<VendorModel>) {
        binding.apply {
            val adapter = ListPaymentVendorAdapter(data)
            rvPesanan.layoutManager = LinearLayoutManager(this@PaymentActivity2, LinearLayoutManager.VERTICAL, false)
            rvPesanan.adapter = adapter
        }
    }

    private fun postDataRegistrasiPembayaran(
        kodeUnik: String,
        idUser: Int,
        idWo: Int,
        idVendor: String,
        waktu: String,
        waktuAcara: String,
    ) {
//        viewModel.postRegistrasiPembayaran(kodeUnik, idUser, idWo, idVendor, waktu, waktuAcara)
    }

    private fun getDataRegistrasiPembayaran() {
        viewModel.getRegistrasiPembayaran().observe(this@PaymentActivity2){ result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@PaymentActivity2)
                is UIState.Success-> setDataSuccessRegistrasiPembayaran(result.data)
                is UIState.Failure-> setDataFailureRegistrasiPembayaran(result.message)
                else->{}
            }
        }
    }

    private fun setDataFailureRegistrasiPembayaran(message: String) {
        loading.alertDialogCancel()
        Toast.makeText(this@PaymentActivity2, "Ada masalah : $message", Toast.LENGTH_SHORT).show()
    }

    private fun setDataSuccessRegistrasiPembayaran(data: ArrayList<ResponseModel>) {
        loading.alertDialogCancel()
        if(data.isNotEmpty()){
            if(data[0].status == "0"){
                UiKitApi.getDefaultInstance().startPaymentUiFlow(
                    activity = this@PaymentActivity2,
                    launcher = launcher,
                    transactionDetails = initTransactionDetails,
                    customerDetails = customerDetails,
                    itemDetails = itemDetails
                )
            }else{
                Toast.makeText(this@PaymentActivity2, "Gagal Registrasi", Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@PaymentActivity2, "Bermasalah di web", Toast.LENGTH_SHORT).show()
        }
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
        viewModel.getPostPesan().observe(this@PaymentActivity2){ result->
            when(result){
                is UIState.Loading -> loading.alertDialogLoading(this@PaymentActivity2)
                is UIState.Failure -> setFailurePostPesan(result.message)
                is UIState.Success -> setSuccessPostPesan(result.data)
                else->{}
            }
        }
    }

    private fun setFailurePostPesan(message: String) {
        loading.alertDialogCancel()
        Toast.makeText(this@PaymentActivity2, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessPostPesan(data: ArrayList<ResponseModel>) {
        loading.alertDialogCancel()
        if(data.isNotEmpty()){
            if(data[0].status=="0"){
                Toast.makeText(this@PaymentActivity2, "Berhasil", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@PaymentActivity2, MainActivity::class.java))
                finish()
            } else{
                Toast.makeText(this@PaymentActivity2, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@PaymentActivity2, "No respon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun konfigurationMidtrans() {
        setLauncher()
        setCustomerDetails()
        setInitTransactionDetails()
        buildUiKit()
    }

    private fun buildUiKit() {
        setInitTransactionDetails()
        UiKitApi.Builder()
            .withContext(this.applicationContext)
            .withMerchantUrl(Constant.MIDTRANS_BASE_URL)
            .withMerchantClientKey(Constant.MIDTRANS_CLIENT_KEY)
            .enableLog(true)
            .build()
        uiKitCustomSetting()
    }

    private fun uiKitCustomSetting() {
        val uIKitCustomSetting = UiKitApi.getDefaultInstance().uiKitSetting
        uIKitCustomSetting.saveCardChecked = true
    }

    private fun setInitTransactionDetails() {
//        initTransactionDetails = SnapTransactionDetail(
//            uuid,
//            totalBiaya
//        )
        initTransactionDetails = SnapTransactionDetail(
            acak,
            totalBiaya
        )
    }

    private fun setCustomerDetails() {
//        var nomorHp = sharedPreferencesLogin.getNomorHp()
//        if(nomorHp == ""){
//            nomorHp = "0"
//        }
//        customerDetails = CustomerDetails(
//            firstName = sharedPreferencesLogin.getNama(),
//            customerIdentifier = "${sharedPreferencesLogin.getIdUser()}",
//            email = "mail@mail.com",
//            phone = nomorHp
//        )
        if(nomorHp == ""){
            nomorHp = "0"
        }

        customerDetails = CustomerDetails(
            firstName = namaLengkap,
            customerIdentifier = "${sharedPreferencesLogin.getIdUser()}",
            email = "mail@mail.com",
            phone = nomorHp
        )
    }

    private fun setLauncher() {
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == RESULT_OK) {
                result.data?.let {
                    val transactionResult = it.getParcelableExtra<com.midtrans.sdk.uikit.api.model.TransactionResult>(
                        UiKitConstants.KEY_TRANSACTION_RESULT)


//                    Toast.makeText(this, "${transactionResult?.transactionId}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val transactionResult = data?.getParcelableExtra<com.midtrans.sdk.uikit.api.model.TransactionResult>(
                UiKitConstants.KEY_TRANSACTION_RESULT)
            if (transactionResult != null) {
                loading.alertDialogLoading(this@PaymentActivity2)
                when (transactionResult.status) {
                    UiKitConstants.STATUS_SUCCESS -> {
                        loading.alertDialogCancel()
                        Toast.makeText(this, "Transaction Finished. ID: " + transactionResult.transactionId, Toast.LENGTH_LONG).show()
//                        fetchDataPembayaran(idUser)
                    }
                    UiKitConstants.STATUS_PENDING -> {
                        loading.alertDialogCancel()
                        Toast.makeText(this, "Transaction Pending. ID: " + transactionResult.transactionId, Toast.LENGTH_LONG).show()
                        acak = kataAcak.getHurufDanAngka()
//                        fetchDataPembayaran(idUser)
                    }
                    UiKitConstants.STATUS_FAILED -> {
                        loading.alertDialogCancel()
                        Toast.makeText(this, "Transaction Failed. ID: " + transactionResult.transactionId, Toast.LENGTH_LONG).show()
                        acak = kataAcak.getHurufDanAngka()
//                        fetchDataPembayaran(idUser)
                    }
                    UiKitConstants.STATUS_CANCELED -> {
                        loading.alertDialogCancel()
                        Toast.makeText(this, "Transaction Cancelled", Toast.LENGTH_LONG).show()
                        acak = kataAcak.getHurufDanAngka()
//                        fetchDataPembayaran(idUser)
                    }
                    UiKitConstants.STATUS_INVALID -> {
                        loading.alertDialogCancel()
                        Toast.makeText(this, "Transaction Invalid. ID: " + transactionResult.transactionId, Toast.LENGTH_LONG).show()
                        acak = kataAcak.getHurufDanAngka()
//                        fetchDataPembayaran(idUser)
                    }
                    else -> {
                        Toast.makeText(this, "Transaction ID: " + transactionResult.transactionId + ". Message: " + transactionResult.status, Toast.LENGTH_LONG).show()
                        loading.alertDialogCancel()
//                        fetchDataPembayaran(idUser)
                    }
                }
            } else {
                Toast.makeText(this@PaymentActivity2, "Gagal Tranksaksi", Toast.LENGTH_LONG).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun catchTanggalDanWaktu(){
        tanggalDanWaktu.selectedDateTime(
            tanggalDanWaktu.tanggalSekarangZonaMakassar(),
            binding.tvTanggalAcara,
            this@PaymentActivity2
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