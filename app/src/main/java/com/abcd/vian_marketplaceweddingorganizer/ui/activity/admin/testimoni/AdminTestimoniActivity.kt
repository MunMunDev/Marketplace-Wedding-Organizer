package com.abcd.vian_marketplaceweddingorganizer.ui.activity.admin.testimoni

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.adapter.admin.AdminTestimoniAdapter
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.TestimoniModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityAdminTestimoniBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogAkunWoBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogHapusBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogKeteranganBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogTestimoniTambahBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.admin.AdminMainActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.KontrolNavigationDrawer
import com.abcd.vian_marketplaceweddingorganizer.utils.LoadingAlertDialog
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import javax.inject.Inject

@AndroidEntryPoint
class AdminTestimoniActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminTestimoniBinding
    private lateinit var kontrolNavigationDrawer: KontrolNavigationDrawer
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    private val viewModel: AdminTestimoniViewModel by viewModels()
    @Inject
    lateinit var loading: LoadingAlertDialog
    private lateinit var adapter: AdminTestimoniAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminTestimoniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setKontrolNavigationDrawer()
        setSharedPreferencesLogin()
        fetchTestimoni()
        getTestimoni()
        getPostUpdateData()
        getPostDeleteData()
    }

    private fun setSharedPreferencesLogin() {
        sharedPreferencesLogin = SharedPreferencesLogin((this@AdminTestimoniActivity))
    }

    @SuppressLint("SetTextI18n")
    private fun setKontrolNavigationDrawer() {
        binding.apply {
            kontrolNavigationDrawer = KontrolNavigationDrawer(this@AdminTestimoniActivity)
            kontrolNavigationDrawer.cekSebagai(navView)
            kontrolNavigationDrawer.onClickItemNavigationDrawer(navView, drawerLayoutMain, myAppBar.ivNavDrawer, this@AdminTestimoniActivity)

            myAppBar.tvTitle.text = "Testimoni Wedding Organizer"
        }
    }

    private fun fetchTestimoni() {
        viewModel.fetchTestimoni()
    }
    private fun getTestimoni(){
        viewModel.getTestimoni().observe(this@AdminTestimoniActivity){result->
            when(result){
                is UIState.Loading-> setStartShimmer()
                is UIState.Failure-> setFailureFetchTestimoni(result.message)
                is UIState.Success-> setSuccessFetchTestimoni(result.data)
            }
        }
    }

    private fun setFailureFetchTestimoni(message: String) {
        setStopShimmer()
        Toast.makeText(this@AdminTestimoniActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessFetchTestimoni(data: ArrayList<TestimoniModel>) {
        setStopShimmer()
        if(data.isNotEmpty()){
            setAdapterTestimoni(data)
        } else{
            Toast.makeText(this@AdminTestimoniActivity, "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdapterTestimoni(data: ArrayList<TestimoniModel>) {
        adapter = AdminTestimoniAdapter(data, object : OnClickItem.ClickAdminTestimoni{

            override fun clickItemNama(title: String, nama: String) {
                showKeterangan(title, nama)
            }

            override fun clickItemVendor(title: String, vendor: String) {
                showKeterangan(title, vendor)
            }

            override fun clickItemTestimoni(title: String, testimoni: String) {
                showKeterangan(title, testimoni)
            }

            override fun clickItemSetting(tempTestimoni: TestimoniModel, it: View) {
                val popupMenu = PopupMenu(this@AdminTestimoniActivity, it)
                popupMenu.inflate(R.menu.popup_edit_hapus)
                popupMenu.setOnMenuItemClickListener(object :
                    PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
                        when (menuItem!!.itemId) {
                            R.id.edit -> {
                                showTestimoni(tempTestimoni)
                                return true
                            }
                            R.id.hapus -> {
                                setShowDialogDelete(tempTestimoni)
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
            rvTestimoni.layoutManager = LinearLayoutManager(this@AdminTestimoniActivity, LinearLayoutManager.VERTICAL, false)
            rvTestimoni.adapter = adapter
        }
    }

    private fun showKeterangan(title:String, keterangan: String) {
        val view = AlertDialogKeteranganBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@AdminTestimoniActivity)
        alertDialog.setView(view.root)
            .setCancelable(true)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            tvTitleKeterangan.text = title
            tvBodyKeterangan.text = keterangan

            btnClose.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun showTestimoni(data: TestimoniModel) {
        val view = AlertDialogTestimoniTambahBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(this@AdminTestimoniActivity)
        alertDialog.setView(view.root)
            .setCancelable(false)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            //set data
            var jumlahBintang = data.bintang!!.trim().toInt()
            val idTestimoni = data.id_testimoni!!
            val komentar = data.testimoni!!

            etTestimoni.setText(komentar)

            when (data.bintang!!.trim().toInt()) {
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
                    Toast.makeText(this@AdminTestimoniActivity, "Masukkan Jumlah Bintang", Toast.LENGTH_SHORT).show()
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

                        postUpdateDataTestimoni(idTestimoni, testimoni, bintang)
                    }

                }
            }
            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun postUpdateDataTestimoni(idTestimoni: String, testimoni: String, bintang: String){
        viewModel.postUpdateTestimoni(
            idTestimoni, testimoni,  bintang
        )
    }

    private fun getPostUpdateData(){
        viewModel.getUpdateTestimoni().observe(this@AdminTestimoniActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@AdminTestimoniActivity)
                is UIState.Success-> setSuccessUpdateData(result.data)
                is UIState.Failure-> setFailureUpdateData(result.message)
            }
        }
    }

    private fun setFailureUpdateData(message: String) {
        Toast.makeText(this@AdminTestimoniActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setSuccessUpdateData(data: ArrayList<ResponseModel>) {
        if(data.isNotEmpty()){
            if(data[0].status == "0"){
                fetchTestimoni()
            } else{
                Toast.makeText(this@AdminTestimoniActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@AdminTestimoniActivity, "Gagal: Ada kesalahan di API", Toast.LENGTH_SHORT).show()
        }
        loading.alertDialogCancel()
    }

    private fun setShowDialogDelete(tempTestimoni: TestimoniModel){
        val view = AlertDialogHapusBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(this@AdminTestimoniActivity)
            .setView(view.root)
            .setCancelable(true)

        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            tvTitle.text = tempTestimoni.testimoni

            btnHapus.setOnClickListener {
                postDeleteDataTestimoni(tempTestimoni.id_testimoni!!)
            }

            btnBatalHapus.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun postDeleteDataTestimoni(idTestimoni: String){
        viewModel.postDeleteTestimoni(idTestimoni)
    }

    private fun getPostDeleteData(){
        viewModel.getDeleteTestimoni().observe(this@AdminTestimoniActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@AdminTestimoniActivity)
                is UIState.Success-> setSuccessDeleteData(result.data)
                is UIState.Failure-> setFailureDeleteData(result.message)
            }
        }
    }

    private fun setFailureDeleteData(message: String) {
        Toast.makeText(this@AdminTestimoniActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setSuccessDeleteData(data: ArrayList<ResponseModel>) {
        if(data.isNotEmpty()){
            if(data[0].status == "0"){
                fetchTestimoni()
            } else{
                Toast.makeText(this@AdminTestimoniActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@AdminTestimoniActivity, "Gagal: Ada kesalahan di API", Toast.LENGTH_SHORT).show()
        }
        loading.alertDialogCancel()
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

    private fun setStartShimmer(){
        binding.apply {
            smTestimoni.startShimmer()
            smTestimoni.visibility = View.VISIBLE
            hsTestimoni.visibility = View.GONE
        }
    }

    private fun setStopShimmer(){
        binding.apply {
            smTestimoni.stopShimmer()
            smTestimoni.visibility = View.GONE
            hsTestimoni.visibility = View.VISIBLE
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@AdminTestimoniActivity, AdminMainActivity::class.java))
        finish()
    }
}