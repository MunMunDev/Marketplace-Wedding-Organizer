package com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.vendor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.adapter.wo.WeddingOrganizerVendorAdapter
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.VendorModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityWeddingOrganizerVendorBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogKeteranganBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogKonfirmasiBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogWoVendorBinding
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
class WeddingOrganizerVendorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeddingOrganizerVendorBinding
    private lateinit var kontrolNavigationDrawer: KontrolNavigationDrawer
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    private val viewModel: WeddingOrganizerVendorViewModel by viewModels()
    @Inject lateinit var loading: LoadingAlertDialog
    private lateinit var adapter: WeddingOrganizerVendorAdapter
    private var idWo = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeddingOrganizerVendorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setKontrolNavigationDrawer()
        setButton()
        setSharedPreferencesLogin()
        getVendor()
        getTambahVendor()
        getUpdateVendor()
        getHapusVendor()
    }

    private fun setKontrolNavigationDrawer() {
        binding.apply {
            kontrolNavigationDrawer = KontrolNavigationDrawer(this@WeddingOrganizerVendorActivity)
            kontrolNavigationDrawer.cekSebagai(navView)
            kontrolNavigationDrawer.onClickItemNavigationDrawer(navView, drawerLayoutMain, myAppBar.ivNavDrawer, this@WeddingOrganizerVendorActivity)

            myAppBar.tvTitle.text = "List Vendor"
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
        sharedPreferencesLogin = SharedPreferencesLogin((this@WeddingOrganizerVendorActivity))
        idWo = sharedPreferencesLogin.getIdWO()
        fetchVendor(idWo)
        Log.d("DetailTAG", "setSharedPreferencesLogin: $idWo")
    }

    private fun fetchVendor(id_wo: Int) {
        viewModel.fetchVendor(id_wo)
    }
    private fun getVendor(){
        viewModel.getVendor().observe(this@WeddingOrganizerVendorActivity){result->
            when(result){
                is UIState.Loading-> setStartShimmer()
                is UIState.Failure-> setFailureFetchVendor(result.message)
                is UIState.Success-> setSuccessFetchVendor(result.data)
            }
        }
    }

    private fun setFailureFetchVendor(message: String) {
        setStopShimmer()
        Toast.makeText(this@WeddingOrganizerVendorActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessFetchVendor(data: ArrayList<VendorModel>) {
        if(data.isNotEmpty()){
            setAdapterVendor(data)
        } else{
            Toast.makeText(this@WeddingOrganizerVendorActivity, "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
        setStopShimmer()
    }

    private fun setAdapterVendor(data: ArrayList<VendorModel>) {
        adapter = WeddingOrganizerVendorAdapter(data, object : OnClickItem.ClickWeddingOrganizerVendor{
            override fun clickItemVendor(vendor: VendorModel, it: View) {
                showKeteranganVendor(vendor.nama_vendor!!)
            }

            override fun clickItemSetting(vendor: VendorModel, it: View) {
                val popupMenu = PopupMenu(this@WeddingOrganizerVendorActivity, it)
                popupMenu.inflate(R.menu.popup_edit_hapus)
                popupMenu.setOnMenuItemClickListener(object :
                    PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
                        when (menuItem!!.itemId) {
                            R.id.edit -> {
                                setShowDialogEdit(vendor)
                                return true
                            }
                            R.id.hapus -> {
                                setShowDialogHapus(vendor)
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
            rvVendor.layoutManager = LinearLayoutManager(this@WeddingOrganizerVendorActivity, LinearLayoutManager.VERTICAL, false)
            rvVendor.adapter = adapter
        }
    }

    private fun showKeteranganVendor(keterangan: String) {
        val view = AlertDialogKeteranganBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@WeddingOrganizerVendorActivity)
        alertDialog.setView(view.root)
            .setCancelable(true)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            tvTitleKeterangan.text = "Keterangan Vendor"
            tvBodyKeterangan.text = keterangan

            btnClose.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun setShowDialogEdit(vendor: VendorModel) {
        val view = AlertDialogWoVendorBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@WeddingOrganizerVendorActivity)
        alertDialog.setView(view.root)
            .setCancelable(true)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            etVendor.setText(vendor.nama_vendor)
            etHarga.setText(vendor.harga!!.toString())

            var cek = true
            if(etVendor.text.toString().trim().isEmpty()){
                etVendor.error = "Tidak Boleh Kosong"
                cek = false
            }
            if(etHarga.text.toString().trim().isEmpty()){
                etVendor.error = "Tidak Boleh Kosong"
                cek = false
            }

            btnSimpan.setOnClickListener {
                if(cek){
                    postUpdateVendor(vendor.id_vendor!!, etVendor.text.toString().trim(), etHarga.text.toString().toInt())

                    dialogInputan.dismiss()
                }
            }

            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun postUpdateVendor(idVendor: Int, vendor: String, harga: Int) {
        viewModel.postUpdateVendor(idVendor, vendor, harga)
    }

    private fun getUpdateVendor(){
        viewModel.getUpdateVendor().observe(this@WeddingOrganizerVendorActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@WeddingOrganizerVendorActivity)
                is UIState.Failure-> setFailureUpdateVendor(result.message)
                is UIState.Success-> setSuccessUpdateVendor(result.data)
            }
        }
    }

    private fun setFailureUpdateVendor(message: String) {
        Toast.makeText(this@WeddingOrganizerVendorActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setSuccessUpdateVendor(data: ArrayList<ResponseModel>) {
        loading.alertDialogCancel()
        if(data.isNotEmpty()){
            if(data[0].status=="0"){
                Toast.makeText(this@WeddingOrganizerVendorActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                fetchVendor(idWo)
            } else{
                Toast.makeText(this@WeddingOrganizerVendorActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@WeddingOrganizerVendorActivity, "gagal", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setShowDialogHapus(vendor: VendorModel) {
        val view = AlertDialogKonfirmasiBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@WeddingOrganizerVendorActivity)
        alertDialog.setView(view.root)
            .setCancelable(true)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            tvTitleKonfirmasi.text = "Yakin Hapus ${vendor.nama_vendor}"
            tvBodyKonfirmasi.text = "Vendor akan terhapus dan tidak dapa dikembalikan"

            btnKonfirmasi.setOnClickListener {
                postHapusVendor(vendor.id_vendor!!)
                dialogInputan.dismiss()
            }

            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun postHapusVendor(idVendor: Int) {
        viewModel.postDeleteVendor(idVendor)
    }

    private fun getHapusVendor(){
        viewModel.getDeleteVendor().observe(this@WeddingOrganizerVendorActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@WeddingOrganizerVendorActivity)
                is UIState.Failure-> setFailureDeleteVendor(result.message)
                is UIState.Success-> setSuccessDeleteVendor(result.data)
            }
        }
    }

    private fun setSuccessDeleteVendor(data: ArrayList<ResponseModel>) {
        loading.alertDialogCancel()
        if(data.isNotEmpty()){
            if(data[0].status=="0"){
                Toast.makeText(this@WeddingOrganizerVendorActivity, "Berhasil Hapus", Toast.LENGTH_SHORT).show()
                fetchVendor(idWo)
            } else{
                Toast.makeText(this@WeddingOrganizerVendorActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@WeddingOrganizerVendorActivity, "Gagal", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFailureDeleteVendor(message: String) {
        Toast.makeText(this@WeddingOrganizerVendorActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setShowDialogTambah() {
        val view = AlertDialogWoVendorBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@WeddingOrganizerVendorActivity)
        alertDialog.setView(view.root)
            .setCancelable(true)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            btnSimpan.setOnClickListener {
                var cek = true
                if(etVendor.text.toString().trim().isEmpty()){
                    etVendor.error = "Tidak Boleh Kosong"
                    cek = false
                }
                if(etHarga.text.toString().trim().isEmpty()){
                    etHarga.error = "Tidak Boleh Kosong"
                    cek = false
                }

                if(cek){
                    postTambahVendor(idWo, etVendor.text.toString().trim(), etHarga.text.toString().trim().toInt())

                    dialogInputan.dismiss()
                }
            }

            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun postTambahVendor(id_wo: Int, vendor: String, harga: Int) {
        viewModel.postTambahVendor(id_wo, vendor, harga)
    }

    private fun getTambahVendor(){
        viewModel.getTambahVendor().observe(this@WeddingOrganizerVendorActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@WeddingOrganizerVendorActivity)
                is UIState.Failure-> setFailureTambahVendor(result.message)
                is UIState.Success-> setSuccessTambahVendor(result.data)
            }
        }
    }

    private fun setFailureTambahVendor(message: String) {
        Toast.makeText(this@WeddingOrganizerVendorActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setSuccessTambahVendor(data: ArrayList<ResponseModel>) {
        loading.alertDialogCancel()
        if(data.isNotEmpty()){
            if(data[0].status=="0"){
                Toast.makeText(this@WeddingOrganizerVendorActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                fetchVendor(idWo)
            } else{
                Toast.makeText(this@WeddingOrganizerVendorActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@WeddingOrganizerVendorActivity, "Gagal", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setStartShimmer(){
        binding.apply {
            smVendor.startShimmer()
            smVendor.visibility = View.VISIBLE
            rvVendor.visibility = View.GONE
        }
    }

    private fun setStopShimmer(){
        binding.apply {
            smVendor.stopShimmer()
            smVendor.visibility = View.GONE
            rvVendor.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@WeddingOrganizerVendorActivity, WeddingOrganizerMainActivity::class.java))
        finish()
    }
}