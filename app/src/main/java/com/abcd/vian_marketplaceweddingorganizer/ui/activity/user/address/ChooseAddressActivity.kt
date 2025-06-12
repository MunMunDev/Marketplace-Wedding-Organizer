package com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.address

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.vian_marketplaceweddingorganizer.adapter.ChooseAddressAdapter
import com.abcd.vian_marketplaceweddingorganizer.data.model.AlamatModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.KabKotaModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.VendorModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityChooseAddressBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogChooseeAddressBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.payment.PaymentActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.KotaKabProvIndonesia
import com.abcd.vian_marketplaceweddingorganizer.utils.LoadingAlertDialog
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChooseAddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseAddressBinding
    private val viewModel: ChooseAddressViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferencesLogin
    @Inject
    lateinit var loading: LoadingAlertDialog
    @Inject
    lateinit var kotaKab: KotaKabProvIndonesia

    private lateinit var vendor: ArrayList<VendorModel>
    private var namaWeddingOrganizer = ""
    private var idWo = 0
    private lateinit var listKotaKab: ArrayList<KabKotaModel>
    private var listNamaKotaKab: ArrayList<String> = arrayListOf()
    private var listNamaKecamatan: ArrayList<String> = arrayListOf()
    private var listIdKecamatan: ArrayList<String> = arrayListOf()

    private lateinit var valueIdKecamatan: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSharedPreferences()
        setButton()
        fetchDataSebelumnnya()
        setAppNavBar()
        fetchKabKota()
        getKabKota()
        fetchAlamat(sharedPreferences.getIdUser())
        getAlamat()
        getUpdateMainAlamat()
        getTambahAlamat()
        getUpdateAlamat()
    }

    private fun setButton() {
        binding.apply {
            appNavbarDrawer.ivBack.setOnClickListener {
                val i = Intent(this@ChooseAddressActivity, PaymentActivity::class.java)
                i.putParcelableArrayListExtra("vendor", vendor)
                i.putExtra("nama_wedding_organizer", namaWeddingOrganizer)
                i.putExtra("idWo", idWo)
                startActivity(i)
                finish()
            }
            btnTambahAlamat.setOnClickListener {
                setShowDialogTambahData()
            }
        }
    }

    private fun fetchDataSebelumnnya() {
        val extras = intent.extras
        if(extras != null) {
            vendor = arrayListOf()
            vendor = intent.getParcelableArrayListExtra("vendor")!!
            namaWeddingOrganizer = intent.getStringExtra("nama_wedding_organizer")!!
            idWo = intent.getIntExtra("idWo", 0)
        }
    }


    private fun setAppNavBar() {
        binding.appNavbarDrawer.apply {
            tvTitle.text = "Pilih Alamat"
            ivNavDrawer.visibility = View.GONE
            ivBack.visibility = View.VISIBLE
        }
    }

    private fun setShowDialogTambahData() {
        val view = AlertDialogChooseeAddressBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(this@ChooseAddressActivity)
        alertDialog.setView(view.root)
            .setCancelable(false)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            // Set kab sulsel
//            val listKotaKabSulsel = kotaKab.kotaKabSulsel()
            val arrayAdapterKotaKab = ArrayAdapter(
                this@ChooseAddressActivity,
                android.R.layout.simple_spinner_item,
                listNamaKotaKab
            )

            arrayAdapterKotaKab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spKabKota.adapter = arrayAdapterKotaKab

            spKabKota.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val kabkota = spKabKota.selectedItem.toString()
                    var list = listKotaKab.filter {
                        it.kab_kota == kabkota
                    }

                    listNamaKecamatan = arrayListOf()
                    listIdKecamatan = arrayListOf()
                    for(value in list[0].listKecamatan!!){
                        listNamaKecamatan.add(value.kecamatan!!)
                        listIdKecamatan.add(value.id_kecamatan!!)
                    }

                    setSpinnerKecamatan(spKecamatan, listNamaKecamatan, listIdKecamatan, 0)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            btnSimpan.setOnClickListener {
                var cek = true
                if(etNamaLengkap.text.toString().trim().isEmpty()){
                    etNamaLengkap.error = "Tidak Boleh Kosong"
                    cek = false
                }
                if(etNomorHp.text.toString().trim().isEmpty()){
                    etNomorHp.error = "Tidak Boleh Kosong"
                    cek = false
                }
                if(etAlamat.text.toString().trim().isEmpty()){
                    etAlamat.error = "Tidak Boleh Kosong"
                    cek = false
                }
                if(etDetailAlamat.text.toString().trim().isEmpty()){
                    etDetailAlamat.error = "Tidak Boleh Kosong"
                    cek = false
                }

                if(cek){
                    val namaLengkap = etNamaLengkap.text.toString()
                    val nomorHp = etNomorHp.text.toString()
                    val alamat = etAlamat.text.toString()
                    val detailAlamat = etDetailAlamat.text.toString()

                    postTambahAlamat(
                        sharedPreferences.getIdUser().toString(),
                        namaLengkap, nomorHp, valueIdKecamatan, alamat, detailAlamat
                    )
                    dialogInputan.dismiss()
                }
            }
            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun setSpinnerKecamatan(
        spKecamatan: Spinner, valueListNamaKecamatan: ArrayList<String>,
        valueListIdKecamatan: ArrayList<String>, idAlamat: Int){
        // set kacamatan
//            val listKecamatan = kotaKab.kotaKabSulsel()
        val arrayAdapterKecamatan = ArrayAdapter(
            this@ChooseAddressActivity,
            android.R.layout.simple_spinner_item,
            valueListNamaKecamatan
        )

        arrayAdapterKecamatan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spKecamatan.adapter = arrayAdapterKecamatan

        spKecamatan.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                valueIdKecamatan = valueListIdKecamatan[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        spKecamatan.setSelection(idAlamat)
    }

    private fun postTambahAlamat(
        idUser: String, namaLengkap: String, nomorHp: String,
        idKecamatan: String, alamat: String, detailAlamat: String
    ) {
        viewModel.postTambahAlamat(idUser, namaLengkap, nomorHp, idKecamatan, alamat, detailAlamat)
    }

    private fun getTambahAlamat(){
        viewModel.getTambahAlamat().observe(this@ChooseAddressActivity){ result->
            when(result){
                is UIState.Loading -> loading.alertDialogLoading(this@ChooseAddressActivity)
                is UIState.Failure -> setFailureTambahAlamat(result.message)
                is UIState.Success -> setSuccessTambahAlamat(result.data)
                else -> {}
            }
        }
    }

    private fun setFailureTambahAlamat(message: String) {
        loading.alertDialogCancel()
        Toast.makeText(this@ChooseAddressActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessTambahAlamat(data: ArrayList<ResponseModel>) {
        loading.alertDialogCancel()
        if(data.isNotEmpty()){
            if(data[0].status=="0"){
                Toast.makeText(this@ChooseAddressActivity, "Berhasil Tambah", Toast.LENGTH_SHORT).show()
                fetchAlamat(sharedPreferences.getIdUser())
            } else{
                Toast.makeText(this@ChooseAddressActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setSharedPreferences() {
        sharedPreferences = SharedPreferencesLogin(this@ChooseAddressActivity)
    }

    private fun fetchKabKota(){
        viewModel.fetchKabKota()
    }

    private fun getKabKota(){
        viewModel.getKabKota().observe(this@ChooseAddressActivity){ result->
            when(result){
                is UIState.Loading->{}
                is UIState.Failure-> setFailureFetchKabKota(result.message)
                is UIState.Success-> setSuccessFetchKabKota(result.data)
            }
        }
    }

    private fun setFailureFetchKabKota(message: String) {

    }

    private fun setSuccessFetchKabKota(data: ArrayList<KabKotaModel>) {
        if(data.isNotEmpty()){
            listKotaKab = data
            for(value in data){
                listNamaKotaKab.add(value.kab_kota!!)
            }
        }
    }

    private fun fetchAlamat(idUser: Int){
        viewModel.fetchDataAlamat(idUser)
    }
    private fun getAlamat(){
        viewModel.getDataAlamat().observe(this@ChooseAddressActivity){ result->
            when(result){
                is UIState.Loading -> setStarShimmer()
                is UIState.Success -> setSuccessFetchAlamat(result.data)
                is UIState.Failure -> setFailureFetchAlamat(result.message)
                else -> {}
            }
        }
    }

    private fun setFailureFetchAlamat(message: String) {
        setStopShimmer()
        Toast.makeText(this@ChooseAddressActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessFetchAlamat(data: ArrayList<AlamatModel>) {
        setStopShimmer()
        if(data.isNotEmpty()){
            setAdapter(data)
        } else{
            Toast.makeText(this@ChooseAddressActivity, "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdapter(data: ArrayList<AlamatModel>) {
        binding.apply {
            val adapter = ChooseAddressAdapter(data, object: OnClickItem.ClickChooseeAddress{
                override fun clickItemPilih(data: AlamatModel, it: View) {
                    postUpdateMainAlamat(data.id_alamat!!)
                }

                override fun clickItemEdit(data: AlamatModel, it: View) {
                    setShowDialogUpdateData(data.id_alamat!!, data.nama_lengkap!!, data.nomor_hp!!, data.alamat!!, data.detail_alamat!!)
                }

            })
            rvAlamat.layoutManager = LinearLayoutManager(this@ChooseAddressActivity, LinearLayoutManager.VERTICAL, false)
            rvAlamat.adapter = adapter
        }
    }

    private fun postUpdateMainAlamat(idAlamat: String) {
        viewModel.postUpdateMainAlamat(idAlamat, sharedPreferences.getIdUser().toString())
    }

    private fun getUpdateMainAlamat(){
        viewModel.getUpdateMainAlamat().observe(this@ChooseAddressActivity){ result->
            when(result){
                is UIState.Loading -> loading.alertDialogLoading(this@ChooseAddressActivity)
                is UIState.Failure -> setFailureUpdateMainAlamat(result.message)
                is UIState.Success -> setSuccessUpdateMainAlamat(result.data)
                else -> {}
            }
        }
    }

    private fun setFailureUpdateMainAlamat(message: String) {
        loading.alertDialogCancel()
        Toast.makeText(this@ChooseAddressActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessUpdateMainAlamat(data: ArrayList<ResponseModel>) {
        loading.alertDialogCancel()
        if(data.isNotEmpty()){
            if(data[0].status=="0"){
                val i = Intent(this@ChooseAddressActivity, PaymentActivity::class.java)
                i.putParcelableArrayListExtra("vendor", vendor)
                i.putExtra("nama_wedding_organizer", namaWeddingOrganizer)
                startActivity(i)
                finish()
            } else{
                Toast.makeText(this@ChooseAddressActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setShowDialogUpdateData(
        idAlamat: String, namaLengkap: String,
        nomorHp: String, alamat: String, detailAlamat: String
    ) {
        val view = AlertDialogChooseeAddressBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(this@ChooseAddressActivity)
        alertDialog.setView(view.root)
            .setCancelable(false)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            etNamaLengkap.setText(namaLengkap)
            etNomorHp.setText(nomorHp)
            etAlamat.setText(alamat)
            etDetailAlamat.setText(detailAlamat)

            var idSelection = 0

            val arrayAdapterKotaKab = ArrayAdapter(
                this@ChooseAddressActivity,
                android.R.layout.simple_spinner_item,
                listNamaKotaKab
            )

            arrayAdapterKotaKab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spKabKota.adapter = arrayAdapterKotaKab

            spKabKota.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val kabkota = spKabKota.selectedItem.toString()
                    var list = listKotaKab.filter {
                        it.kab_kota == kabkota
                    }

                    listNamaKecamatan = arrayListOf()
                    listIdKecamatan = arrayListOf()
                    for(value in list[0].listKecamatan!!){
                        listNamaKecamatan.add(value.kecamatan!!)
                        listIdKecamatan.add(value.id_kecamatan!!)
                    }

                    setSpinnerKecamatan(spKecamatan, listNamaKecamatan, listIdKecamatan, idSelection)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            btnSimpan.setOnClickListener {
                var cek = true
                if(etNamaLengkap.text.toString().trim().isEmpty()){
                    etNamaLengkap.error = "Tidak Boleh Kosong"
                    cek = false
                }
                if(etNomorHp.text.toString().trim().isEmpty()){
                    etNomorHp.error = "Tidak Boleh Kosong"
                    cek = false
                }
                if(etAlamat.text.toString().trim().isEmpty()){
                    etAlamat.error = "Tidak Boleh Kosong"
                    cek = false
                }
                if(etDetailAlamat.text.toString().trim().isEmpty()){
                    etDetailAlamat.error = "Tidak Boleh Kosong"
                    cek = false
                }

                if(cek){
                    val valueNamaLengkap = etNamaLengkap.text.toString()
                    val valueNomorHp = etNomorHp.text.toString()
                    val valueAlamat = etAlamat.text.toString()
                    val valueDetailAlamat = etDetailAlamat.text.toString()

                    postUpdateAlamat(
                        idAlamat, sharedPreferences.getIdUser().toString(),
                        valueNamaLengkap, valueNomorHp, valueIdKecamatan, valueAlamat, valueDetailAlamat
                    )

                    dialogInputan.dismiss()
                }
            }
            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun postUpdateAlamat(
        idAlamat: String, idUser: String, namaLengkap: String,
        nomorHp: String, idKecamatan:String, alamat: String, detailAlamat: String
    ) {
        viewModel.postUpdateAlamat(idAlamat, idUser, namaLengkap, nomorHp, idKecamatan, alamat, detailAlamat)
    }

    private fun getUpdateAlamat(){
        viewModel.getUpdateAlamat().observe(this@ChooseAddressActivity){ result->
            when(result){
                is UIState.Loading -> loading.alertDialogLoading(this@ChooseAddressActivity)
                is UIState.Failure -> setFailureUpdateAlamat(result.message)
                is UIState.Success -> setSuccessUpdateAlamat(result.data)
                else -> {}
            }
        }
    }

    private fun setFailureUpdateAlamat(message: String) {
        loading.alertDialogCancel()
        Toast.makeText(this@ChooseAddressActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessUpdateAlamat(data: ArrayList<ResponseModel>) {
        loading.alertDialogCancel()
        if(data.isNotEmpty()){
            if(data[0].status=="0"){
                Toast.makeText(this@ChooseAddressActivity, "Berhasil Update", Toast.LENGTH_SHORT).show()
                fetchAlamat(sharedPreferences.getIdUser())
            } else{
                Toast.makeText(this@ChooseAddressActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setStarShimmer(){
        binding.apply {
            binding.apply {
                rvAlamat.visibility = View.GONE

                smAlamat.visibility = View.VISIBLE
                smAlamat.startShimmer()
            }
        }
    }

    private fun setStopShimmer(){
        binding.apply {
            binding.apply {
                rvAlamat.visibility = View.VISIBLE

                smAlamat.visibility = View.GONE
                smAlamat.stopShimmer()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val i = Intent(this@ChooseAddressActivity, PaymentActivity::class.java)
        i.putParcelableArrayListExtra("vendor", vendor)
        i.putExtra("nama_wedding_organizer", namaWeddingOrganizer)
        startActivity(i)
        finish()
    }

}