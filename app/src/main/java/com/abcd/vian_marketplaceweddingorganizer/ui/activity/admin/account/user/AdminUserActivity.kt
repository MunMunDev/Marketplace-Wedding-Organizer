package com.abcd.vian_marketplaceweddingorganizer.ui.activity.admin.account.user

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
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.adapter.admin.AdminUserAdapter
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.UsersModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityAdminUserBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogAkunWoBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogKeteranganBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogShowImageBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.admin.AdminMainActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.register.RegisterActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.Constant
import com.abcd.vian_marketplaceweddingorganizer.utils.KontrolNavigationDrawer
import com.abcd.vian_marketplaceweddingorganizer.utils.LoadingAlertDialog
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.ArrayList
import javax.inject.Inject

@AndroidEntryPoint
class AdminUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminUserBinding
    private lateinit var kontrolNavigationDrawer: KontrolNavigationDrawer
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    private val viewModel: AdminUserViewModel by viewModels()
    @Inject
    lateinit var loading: LoadingAlertDialog
    private lateinit var adapter: AdminUserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setKontrolNavigationDrawer()
        setButton()
        setSharedPreferencesLogin()
        fetchUser()
        getUser()
        getPostTambahData()
        getPostUpdateData()
    }

    private fun setSharedPreferencesLogin() {
        sharedPreferencesLogin = SharedPreferencesLogin((this@AdminUserActivity))
    }

    @SuppressLint("SetTextI18n")
    private fun setKontrolNavigationDrawer() {
        binding.apply {
            kontrolNavigationDrawer = KontrolNavigationDrawer(this@AdminUserActivity)
            kontrolNavigationDrawer.cekSebagai(navView)
            kontrolNavigationDrawer.onClickItemNavigationDrawer(navView, drawerLayoutMain, myAppBar.ivNavDrawer, this@AdminUserActivity)

            myAppBar.tvTitle.text = "Akun Wedding Organizer"
        }
    }

    private fun setButton() {
        binding.apply {
            btnTambah.setOnClickListener {
                setShowDialogTambah()
            }
        }
    }



    private fun setShowDialogTambah() {
        val view = AlertDialogAkunWoBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@AdminUserActivity)
        alertDialog.setView(view.root)
            .setCancelable(true)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {

            tvEditDeskripsi.visibility = View.GONE
            etEditDeskripsi.visibility = View.GONE
            tvEditLogo.visibility = View.GONE
            etEditLogo.visibility = View.GONE

            btnSimpan.setOnClickListener {

                var cek = false
                var checkLogo = false
                if (etEditNama.toString().isEmpty()) {
                    etEditNama.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if (etEditNomorHp.toString().isEmpty()) {
                    etEditNomorHp.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if (etEditUsername.toString().isEmpty()) {
                    etEditUsername.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if (etEditPassword.toString().isEmpty()) {
                    etEditPassword.error = "Tidak Boleh Kosong"
                    cek = true
                }

                if (!cek) {
                    postTambahDataUser(
                        etEditNama.text.toString(),
                        etEditAlamat.text.toString(),
                        etEditNomorHp.text.toString(),
                        etEditUsername.text.toString(),
                        etEditPassword.text.toString(),
                        etEditDeskripsi.text.toString()
                    )

                    dialogInputan.dismiss()
                }
            }

            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun postTambahDataUser(nama: String, alamat: String, nomorHp: String, username: String, password: String, deskripsi: String){
        viewModel.postTambahUser(
            nama, alamat, nomorHp, username, password
        )
    }

    private fun getPostTambahData(){
        viewModel.getTambahUser().observe(this@AdminUserActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@AdminUserActivity)
                is UIState.Success-> setSuccessTambahData(result.data)
                is UIState.Failure-> setFailureTambahData(result.message)
            }
        }
    }

    private fun setFailureTambahData(message: String) {
        Toast.makeText(this@AdminUserActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setSuccessTambahData(data: ArrayList<ResponseModel>) {
        if(data.isNotEmpty()){
            if(data[0].status == "0"){
                fetchUser()
            } else{
                Toast.makeText(this@AdminUserActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@AdminUserActivity, "Gagal: Ada kesalahan di API", Toast.LENGTH_SHORT).show()
        }
        loading.alertDialogCancel()
    }

    private fun fetchUser() {
        viewModel.fetchUser()
    }
    private fun getUser(){
        viewModel.getUser().observe(this@AdminUserActivity){result->
            when(result){
                is UIState.Loading-> setStartShimmer()
                is UIState.Failure-> setFailureFetchUser(result.message)
                is UIState.Success-> setSuccessFetchUser(result.data)
            }
        }
    }

    private fun setFailureFetchUser(message: String) {
        setStopShimmer()
        Toast.makeText(this@AdminUserActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessFetchUser(data: ArrayList<UsersModel>) {
        setStopShimmer()
        if(data.isNotEmpty()){
            setAdapterUser(data)
        } else{
            Toast.makeText(this@AdminUserActivity, "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdapterUser(data: ArrayList<UsersModel>) {
        adapter = AdminUserAdapter(data, object : OnClickItem.ClickAdminUser{

            override fun clickItemNama(title: String, nama: String) {
                showKeterangan(title, nama)
            }

            override fun clickItemAlamat(title: String, alamat: String) {
                showKeterangan(title, alamat)
            }

            override fun clickItemUsername(title: String, username: String) {
                showKeterangan(title, username)
            }

            override fun clickItemSetting(wo: UsersModel, it: View) {
                val popupMenu = PopupMenu(this@AdminUserActivity, it)
                popupMenu.inflate(R.menu.popup_edit)
                popupMenu.setOnMenuItemClickListener(object :
                    PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
                        when (menuItem!!.itemId) {
                            R.id.edit -> {
                                setShowDialogEdit(wo)
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
            rvUser.layoutManager = LinearLayoutManager(this@AdminUserActivity, LinearLayoutManager.VERTICAL, false)
            rvUser.adapter = adapter
        }
    }

    private fun showKeterangan(title:String, keterangan: String) {
        val view = AlertDialogKeteranganBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@AdminUserActivity)
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

    private fun setShowDialogEdit(user: UsersModel) {
        val view = AlertDialogAkunWoBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@AdminUserActivity)
        alertDialog.setView(view.root)
            .setCancelable(true)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {

            etEditNama.setText(user.nama)
            etEditAlamat.setText(user.alamat)
            etEditNomorHp.setText(user.nomorHp)
            etEditUsername.setText(user.username)
            etEditPassword.setText(user.password)

            tvEditDeskripsi.visibility = View.GONE
            etEditDeskripsi.visibility = View.GONE
            tvEditLogo.visibility = View.GONE
            etEditLogo.visibility = View.GONE

            btnSimpan.setOnClickListener {

                var cek = false
                var checkLogo = false
                if (etEditNama.toString().isEmpty()) {
                    etEditNama.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if (etEditNomorHp.toString().isEmpty()) {
                    etEditNomorHp.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if (etEditUsername.toString().isEmpty()) {
                    etEditUsername.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if (etEditPassword.toString().isEmpty()) {
                    etEditPassword.error = "Tidak Boleh Kosong"
                    cek = true
                }

                if (!cek) {
                    postUpdateDataUser(
                        user.idUser!!,
                        etEditNama.text.toString(),
                        etEditAlamat.text.toString(),
                        etEditNomorHp.text.toString(),
                        etEditUsername.text.toString(),
                        etEditPassword.text.toString(),
                        user.username!!,
                    )

                    dialogInputan.dismiss()
                }
            }

            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun postUpdateDataUser(idUser: Int, nama: String, alamat: String, nomorHp: String, username: String, password: String, usernameLama: String){
        viewModel.postUpdateUser(
            idUser, nama, alamat, nomorHp, username,
            password,  usernameLama
        )
    }

    private fun getPostUpdateData(){
        viewModel.getUpdateUser().observe(this@AdminUserActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@AdminUserActivity)
                is UIState.Success-> setSuccessUpdateData(result.data)
                is UIState.Failure-> setFailureUpdateData(result.message)
            }
        }
    }

    private fun setFailureUpdateData(message: String) {
        Toast.makeText(this@AdminUserActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setSuccessUpdateData(data: ArrayList<ResponseModel>) {
        if(data.isNotEmpty()){
            if(data[0].status == "0"){
                fetchUser()
            } else{
                Toast.makeText(this@AdminUserActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@AdminUserActivity, "Gagal: Ada kesalahan di API", Toast.LENGTH_SHORT).show()
        }
        loading.alertDialogCancel()
    }


    private fun setStartShimmer(){
        binding.apply {
            smUser.startShimmer()
            smUser.visibility = View.VISIBLE
            hsUser.visibility = View.GONE
        }
    }

    private fun setStopShimmer(){
        binding.apply {
            smUser.stopShimmer()
            smUser.visibility = View.GONE
            hsUser.visibility = View.VISIBLE
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@AdminUserActivity, AdminMainActivity::class.java))
        finish()
    }
}