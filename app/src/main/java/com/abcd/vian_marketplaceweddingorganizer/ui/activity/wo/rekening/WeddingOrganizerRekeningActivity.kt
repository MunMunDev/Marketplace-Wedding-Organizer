package com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.rekening

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.adapter.wo.WeddingOrganizerRekeningAdapter
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.RekeningModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityWeddingOrganizerRekeningBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogKeteranganBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogKonfirmasiBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogWoRekeningBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.main.WeddingOrganizerMainActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.KontrolNavigationDrawer
import com.abcd.vian_marketplaceweddingorganizer.utils.LoadingAlertDialog
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import javax.inject.Inject

@AndroidEntryPoint
class WeddingOrganizerRekeningActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeddingOrganizerRekeningBinding
    private lateinit var kontrolNavigationDrawer: KontrolNavigationDrawer
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    private val viewModel: WeddingOrganizerRekeningViewModel by viewModels()
    @Inject
    lateinit var loading: LoadingAlertDialog
    private lateinit var adapter: WeddingOrganizerRekeningAdapter
    private var idWo = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeddingOrganizerRekeningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setKontrolNavigationDrawer()
        setButton()
        setSharedPreferencesLogin()
        getRekening()
        getTambahRekening()
        getUpdateRekening()
        getHapusRekening()
    }

    private fun setKontrolNavigationDrawer() {
        binding.apply {
            kontrolNavigationDrawer = KontrolNavigationDrawer(this@WeddingOrganizerRekeningActivity)
            kontrolNavigationDrawer.cekSebagai(navView)
            kontrolNavigationDrawer.onClickItemNavigationDrawer(navView, drawerLayoutMain, myAppBar.ivNavDrawer, this@WeddingOrganizerRekeningActivity)

            myAppBar.tvTitle.text = "List Rekening"
        }
    }

    private fun setButton() {
        binding.apply {
            btnTambah.setOnClickListener {
                setShowDialogTambah()
            }
        }
    }

    private fun setSharedPreferencesLogin() {
        sharedPreferencesLogin = SharedPreferencesLogin((this@WeddingOrganizerRekeningActivity))
        idWo = sharedPreferencesLogin.getIdWO()
        fetchRekening(idWo)
        Log.d("DetailTAG", "setSharedPreferencesLogin: $idWo")
    }

    private fun fetchRekening(id_wo: Int) {
        viewModel.fetchRekening(id_wo)
    }
    private fun getRekening(){
        viewModel.getRekening().observe(this@WeddingOrganizerRekeningActivity){result->
            when(result){
                is UIState.Loading-> setStartShimmer()
                is UIState.Failure-> setFailureFetchRekening(result.message)
                is UIState.Success-> setSuccessFetchRekening(result.data)
            }
        }
    }

    private fun setFailureFetchRekening(message: String) {
        setStopShimmer()
        Toast.makeText(this@WeddingOrganizerRekeningActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessFetchRekening(data: ArrayList<RekeningModel>) {
        if(data.isNotEmpty()){
            setAdapterRekening(data)
        } else{
            Toast.makeText(this@WeddingOrganizerRekeningActivity, "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
        setStopShimmer()
    }

    private fun setAdapterRekening(data: ArrayList<RekeningModel>) {
        adapter = WeddingOrganizerRekeningAdapter(data, object : OnClickItem.ClickWeddingOrganizerRekening{
            override fun clickItemNama(ket: String, nama: String, it: View) {
                showKeteranganRekening(ket, nama)
            }

            override fun clickItemSetting(rekening: RekeningModel, it: View) {
                val popupMenu = PopupMenu(this@WeddingOrganizerRekeningActivity, it)
                popupMenu.inflate(R.menu.popup_edit_hapus)
                popupMenu.setOnMenuItemClickListener(object :
                    PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
                        when (menuItem!!.itemId) {
                            R.id.edit -> {
                                setShowDialogEdit(rekening)
                                return true
                            }
                            R.id.hapus -> {
                                setShowDialogHapus(rekening)
                                return true
                            }
                        }
                        return true
                    }

                })
                popupMenu.show()
            }

        })

        binding.apply {
            rvRekening.layoutManager = LinearLayoutManager(this@WeddingOrganizerRekeningActivity, LinearLayoutManager.VERTICAL, false)
            rvRekening.adapter = adapter
        }
    }

    private fun showKeteranganRekening(keterangan: String, value:String) {
        val view = AlertDialogKeteranganBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@WeddingOrganizerRekeningActivity)
        alertDialog.setView(view.root)
            .setCancelable(true)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            tvTitleKeterangan.text = keterangan
            tvBodyKeterangan.text = value

            btnClose.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun setShowDialogEdit(rekening: RekeningModel) {
        val view = AlertDialogWoRekeningBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@WeddingOrganizerRekeningActivity)
        alertDialog.setView(view.root)
            .setCancelable(true)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            var selectedValue = ""
            val arrayAdapter = ArrayAdapter.createFromResource(
                this@WeddingOrganizerRekeningActivity,
                R.array.rekening,
                android.R.layout.simple_spinner_item
            )
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spRekening.adapter = arrayAdapter

            spRekening.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedValue = spRekening.selectedItem.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
            spRekening.adapter = arrayAdapter

            etNoRekening.setText(rekening.no_rekening!!.toString())
            etNama.setText(rekening.nama)

            var cek = true
            if(etNoRekening.text.toString().trim().isEmpty()){
                etNoRekening.error = "Tidak Boleh Kosong"
                cek = false
            }

            btnSimpan.setOnClickListener {
                if(cek){
                    val noRekening = etNoRekening.text.toString().toInt()
                    val nama = etNama.text.toString()
                    postUpdateRekening(rekening.id_rekening!!.toInt(), selectedValue, noRekening, nama)

                    dialogInputan.dismiss()
                }
            }

            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun postUpdateRekening(idRekening: Int, rekening: String, harga: Int, nama:String) {
        viewModel.postUpdateRekening(idRekening, rekening, harga, nama)
    }

    private fun getUpdateRekening(){
        viewModel.getUpdateRekening().observe(this@WeddingOrganizerRekeningActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@WeddingOrganizerRekeningActivity)
                is UIState.Failure-> setFailureUpdateRekening(result.message)
                is UIState.Success-> setSuccessUpdateRekening(result.data)
            }
        }
    }

    private fun setFailureUpdateRekening(message: String) {
        Toast.makeText(this@WeddingOrganizerRekeningActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setSuccessUpdateRekening(data: ArrayList<ResponseModel>) {
        loading.alertDialogCancel()
        if(data.isNotEmpty()){
            if(data[0].status=="0"){
                Toast.makeText(this@WeddingOrganizerRekeningActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                fetchRekening(idWo)
            } else{
                Toast.makeText(this@WeddingOrganizerRekeningActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@WeddingOrganizerRekeningActivity, "gagal", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setShowDialogHapus(rekening: RekeningModel) {
        val view = AlertDialogKonfirmasiBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@WeddingOrganizerRekeningActivity)
        alertDialog.setView(view.root)
            .setCancelable(true)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            tvTitleKonfirmasi.text = "Yakin Hapus ${rekening.no_rekening}"
            tvBodyKonfirmasi.text = "Rekening akan terhapus dan tidak dapa dikembalikan"

            btnKonfirmasi.setOnClickListener {
                postHapusRekening(rekening.id_rekening!!.toInt())
                dialogInputan.dismiss()
            }

            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun postHapusRekening(idRekening: Int) {
        viewModel.postDeleteRekening(idRekening)
    }

    private fun getHapusRekening(){
        viewModel.getDeleteRekening().observe(this@WeddingOrganizerRekeningActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@WeddingOrganizerRekeningActivity)
                is UIState.Failure-> setFailureDeleteRekening(result.message)
                is UIState.Success-> setSuccessDeleteRekening(result.data)
            }
        }
    }

    private fun setSuccessDeleteRekening(data: ArrayList<ResponseModel>) {
        loading.alertDialogCancel()
        if(data.isNotEmpty()){
            if(data[0].status=="0"){
                Toast.makeText(this@WeddingOrganizerRekeningActivity, "Berhasil Hapus", Toast.LENGTH_SHORT).show()
                fetchRekening(idWo)
            } else{
                Toast.makeText(this@WeddingOrganizerRekeningActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@WeddingOrganizerRekeningActivity, "Gagal", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFailureDeleteRekening(message: String) {
        Toast.makeText(this@WeddingOrganizerRekeningActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setShowDialogTambah() {
        val view = AlertDialogWoRekeningBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@WeddingOrganizerRekeningActivity)
        alertDialog.setView(view.root)
            .setCancelable(true)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            var selectedValue = ""
            val arrayAdapter = ArrayAdapter.createFromResource(
                this@WeddingOrganizerRekeningActivity,
                R.array.rekening,
                android.R.layout.simple_spinner_item
            )
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spRekening.adapter = arrayAdapter

            spRekening.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedValue = spRekening.selectedItem.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
            spRekening.adapter = arrayAdapter

            btnSimpan.setOnClickListener {
                var cek = true
                if(etNoRekening.text.toString().trim().isEmpty()){
                    etNoRekening.error = "Tidak Boleh Kosong"
                    cek = false
                }

                if(cek){
                    val noRekening = etNoRekening.text.toString()
                    val nama = etNama.text.toString()

                    postTambahRekening(idWo, selectedValue, noRekening, nama)

                    dialogInputan.dismiss()
                }
            }

            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun postTambahRekening(id_wo: Int, rekening: String, noRekening: String, nama:String) {
        viewModel.postTambahRekening(id_wo, rekening, noRekening, nama)
    }

    private fun getTambahRekening(){
        viewModel.getTambahRekening().observe(this@WeddingOrganizerRekeningActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@WeddingOrganizerRekeningActivity)
                is UIState.Failure-> setFailureTambahRekening(result.message)
                is UIState.Success-> setSuccessTambahRekening(result.data)
            }
        }
    }

    private fun setFailureTambahRekening(message: String) {
        Toast.makeText(this@WeddingOrganizerRekeningActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setSuccessTambahRekening(data: ArrayList<ResponseModel>) {
        loading.alertDialogCancel()
        if(data.isNotEmpty()){
            if(data[0].status=="0"){
                Toast.makeText(this@WeddingOrganizerRekeningActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                fetchRekening(idWo)
            } else{
                Toast.makeText(this@WeddingOrganizerRekeningActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@WeddingOrganizerRekeningActivity, "Gagal", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setStartShimmer(){
        binding.apply {
            smRekening.startShimmer()
            smRekening.visibility = View.VISIBLE
            rvRekening.visibility = View.GONE
        }
    }

    private fun setStopShimmer(){
        binding.apply {
            smRekening.stopShimmer()
            smRekening.visibility = View.GONE
            rvRekening.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@WeddingOrganizerRekeningActivity, WeddingOrganizerMainActivity::class.java))
        finish()
    }
}