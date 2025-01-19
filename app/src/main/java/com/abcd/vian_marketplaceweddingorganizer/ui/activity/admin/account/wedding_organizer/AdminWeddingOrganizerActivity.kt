package com.abcd.vian_marketplaceweddingorganizer.ui.activity.admin.account.wedding_organizer

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
import com.abcd.vian_marketplaceweddingorganizer.adapter.admin.AdminWeddingOrganizerAdapter
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.UsersModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityAdminWeddingOrganizerBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogAkunWoBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogKeteranganBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogShowImageBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.register.RegisterActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.main.WeddingOrganizerMainActivity
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
class AdminWeddingOrganizerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminWeddingOrganizerBinding
    private lateinit var kontrolNavigationDrawer: KontrolNavigationDrawer
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    private val viewModel: AdminWeddingOrganizerViewModel by viewModels()
    @Inject
    lateinit var loading: LoadingAlertDialog
    private lateinit var adapter: AdminWeddingOrganizerAdapter
    private var fileImage: MultipartBody.Part? = null
    private var dialog: AlertDialogAkunWoBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminWeddingOrganizerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setKontrolNavigationDrawer()
        setButton()
        setSharedPreferencesLogin()
        fetchWeddingOrganizer()
        getWeddingOrganizer()
        getPostTambahData()
        getPostUpdateData()
    }

    private fun setSharedPreferencesLogin() {
        sharedPreferencesLogin = SharedPreferencesLogin((this@AdminWeddingOrganizerActivity))
    }

    @SuppressLint("SetTextI18n")
    private fun setKontrolNavigationDrawer() {
        binding.apply {
            kontrolNavigationDrawer = KontrolNavigationDrawer(this@AdminWeddingOrganizerActivity)
            kontrolNavigationDrawer.cekSebagai(navView)
            kontrolNavigationDrawer.onClickItemNavigationDrawer(navView, drawerLayoutMain, myAppBar.ivNavDrawer, this@AdminWeddingOrganizerActivity)

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

        val alertDialog = AlertDialog.Builder(this@AdminWeddingOrganizerActivity)
        alertDialog.setView(view.root)
            .setCancelable(true)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            dialog = view

            etEditLogo.setOnClickListener {
                if(checkPermission()){
                    pickImageFile()
                } else{
                    requestPermission()
                }
            }
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
                if (etEditDeskripsi.toString().isEmpty()) {
                    etEditDeskripsi.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if (etEditLogo.text.toString() == resources.getString(R.string.ket_klik_file)) {
                    cek = true
                }

                if (!cek) {
                    postTambahDataWoWithImage(
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

    private fun postTambahDataWoWithImage(nama: String, alamat: String, nomorHp: String, username: String, password: String, deskripsi: String){
        viewModel.postTambahWoWithImage(
            convertStringToMultipartBody(""),
            convertStringToMultipartBody(nama),
            convertStringToMultipartBody(alamat),
            convertStringToMultipartBody(nomorHp),
            convertStringToMultipartBody(username),
            convertStringToMultipartBody(password),
            convertStringToMultipartBody("wo"),
            convertStringToMultipartBody(deskripsi),
            fileImage!!
        )
    }

    private fun getPostTambahData(){
        viewModel.getTambahWeddingOrganizer().observe(this@AdminWeddingOrganizerActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@AdminWeddingOrganizerActivity)
                is UIState.Success-> setSuccessTambahData(result.data)
                is UIState.Failure-> setFailureTambahData(result.message)
            }
        }
    }

    private fun setFailureTambahData(message: String) {
        Toast.makeText(this@AdminWeddingOrganizerActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setSuccessTambahData(data: ArrayList<ResponseModel>) {
        if(data.isNotEmpty()){
            if(data[0].status == "0"){
                fetchWeddingOrganizer()
            } else{
                Toast.makeText(this@AdminWeddingOrganizerActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@AdminWeddingOrganizerActivity, "Gagal: Ada kesalahan di API", Toast.LENGTH_SHORT).show()
        }
        loading.alertDialogCancel()
    }

    private fun fetchWeddingOrganizer() {
        viewModel.fetchWeddingOrganizer()
    }
    private fun getWeddingOrganizer(){
        viewModel.getWeddingOrganizer().observe(this@AdminWeddingOrganizerActivity){result->
            when(result){
                is UIState.Loading-> setStartShimmer()
                is UIState.Failure-> setFailureFetchWeddingOrganizer(result.message)
                is UIState.Success-> setSuccessFetchWeddingOrganizer(result.data)
            }
        }
    }

    private fun setFailureFetchWeddingOrganizer(message: String) {
        setStopShimmer()
        Toast.makeText(this@AdminWeddingOrganizerActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessFetchWeddingOrganizer(data: ArrayList<UsersModel>) {
        setStopShimmer()
        if(data.isNotEmpty()){
            setAdapterWeddingOrganizer(data)
        } else{
            Toast.makeText(this@AdminWeddingOrganizerActivity, "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdapterWeddingOrganizer(data: ArrayList<UsersModel>) {
        adapter = AdminWeddingOrganizerAdapter(data, object : OnClickItem.ClickAdminWeddingOrganizer{

            override fun clickItemNama(title: String, nama: String) {
                showKeterangan(title, nama)
            }

            override fun clickItemAlamat(title: String, alamat: String) {
                showKeterangan(title, alamat)
            }

            override fun clickItemUsername(title: String, username: String) {
                showKeterangan(title, username)
            }

            override fun clickItemDeskripsi(title: String, deskripsi: String) {
                showKeterangan(title, deskripsi)
            }

            override fun clickItemGambar(title: String, gambar: String) {
                setShowImage(title, gambar)
            }

            override fun clickItemSetting(wo: UsersModel, it: View) {
                val popupMenu = PopupMenu(this@AdminWeddingOrganizerActivity, it)
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
            rvWeddingOrganizer.layoutManager = LinearLayoutManager(this@AdminWeddingOrganizerActivity, LinearLayoutManager.VERTICAL, false)
            rvWeddingOrganizer.adapter = adapter
        }
    }

    private fun showKeterangan(title:String, keterangan: String) {
        val view = AlertDialogKeteranganBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@AdminWeddingOrganizerActivity)
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

    private fun setShowImage(title:String, gambar: String) {
        val view = AlertDialogShowImageBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@AdminWeddingOrganizerActivity)
        alertDialog.setView(view.root)
            .setCancelable(false)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            tvTitle.text = title
            btnClose.setOnClickListener {
                dialogInputan.dismiss()
            }
        }

        Glide.with(this@AdminWeddingOrganizerActivity)
            .load("${Constant.BASE_URL}${Constant.LOCATION_GAMBAR}$gambar") // URL Gambar
            .error(R.drawable.background_main2)
            .into(view.ivShowImage) // imageView mana yang akan diterapkan

    }

    private fun setShowDialogEdit(wo: UsersModel) {
        val view = AlertDialogAkunWoBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@AdminWeddingOrganizerActivity)
        alertDialog.setView(view.root)
            .setCancelable(true)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            dialog = view

            etEditNama.setText(wo.nama)
            etEditAlamat.setText(wo.alamat)
            etEditNomorHp.setText(wo.nomorHp)
            etEditUsername.setText(wo.username)
            etEditPassword.setText(wo.password)
            etEditDeskripsi.setText(wo.deskripsi_wo)

            etEditLogo.setOnClickListener {
                if(checkPermission()){
                    pickImageFile()
                } else{
                    requestPermission()
                }
            }

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
                if (etEditDeskripsi.toString().isEmpty()) {
                    etEditDeskripsi.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if (etEditLogo.text.toString() == resources.getString(R.string.ket_klik_file)) {
                    checkLogo = true
                }

                if (!cek) {
                    if (checkLogo) {
                        postUpdateDataWo(
                            wo.idUser!!,
                            wo.id_wo!!,
                            etEditNama.text.toString(),
                            etEditAlamat.text.toString(),
                            etEditNomorHp.text.toString(),
                            etEditUsername.text.toString(),
                            etEditPassword.text.toString(),
                            wo.username!!,
                            etEditDeskripsi.text.toString()
                        )
                    } else {
                        postUpdateDataWoWithImage(
                            wo.idUser!!.toString(),
                            wo.id_wo!!.toString(),
                            etEditNama.text.toString(),
                            etEditAlamat.text.toString(),
                            etEditNomorHp.text.toString(),
                            etEditUsername.text.toString(),
                            etEditPassword.text.toString(),
                            wo.username!!,
                            etEditDeskripsi.text.toString()
                        )
                    }

                    dialogInputan.dismiss()
                }
            }

            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun postUpdateDataWo(idUser: Int, idWo: Int, nama: String, alamat: String, nomorHp: String, username: String, password: String, passwordLama: String, deskripsi: String){
        viewModel.postUpdateWo(
            idUser, idWo, nama, alamat, nomorHp, username,
            password, "wo", passwordLama, deskripsi
        )
    }

    private fun postUpdateDataWoWithImage(idUser: String, idWo: String, nama: String, alamat: String, nomorHp: String, username: String, password: String,  passwordLama: String, deskripsi: String){
        viewModel.postUpdateWoWithImage(
            convertStringToMultipartBody("update_admin_akun_wo_with_image"),
            convertStringToMultipartBody(idUser),
            convertStringToMultipartBody(idWo),
            convertStringToMultipartBody(nama),
            convertStringToMultipartBody(alamat),
            convertStringToMultipartBody(nomorHp),
            convertStringToMultipartBody(username),
            convertStringToMultipartBody(password),
            convertStringToMultipartBody("wo"),
            convertStringToMultipartBody(passwordLama),
            convertStringToMultipartBody(deskripsi),
            fileImage!!
        )
    }

    private fun getPostUpdateData(){
        viewModel.getUpdateWeddingOrganizer().observe(this@AdminWeddingOrganizerActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@AdminWeddingOrganizerActivity)
                is UIState.Success-> setSuccessUpdateData(result.data)
                is UIState.Failure-> setFailureUpdateData(result.message)
            }
        }
    }

    private fun setFailureUpdateData(message: String) {
        Toast.makeText(this@AdminWeddingOrganizerActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setSuccessUpdateData(data: ArrayList<ResponseModel>) {
        if(data.isNotEmpty()){
            if(data[0].status == "0"){
                fetchWeddingOrganizer()
            } else{
                Toast.makeText(this@AdminWeddingOrganizerActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@AdminWeddingOrganizerActivity, "Gagal: Ada kesalahan di API", Toast.LENGTH_SHORT).show()
        }
        loading.alertDialogCancel()
    }


    private fun setStartShimmer(){
        binding.apply {
            smWeddingOrganizer.startShimmer()
            smWeddingOrganizer.visibility = View.VISIBLE
            hsWeddingOrganizer.visibility = View.GONE
        }
    }

    private fun setStopShimmer(){
        binding.apply {
            smWeddingOrganizer.stopShimmer()
            smWeddingOrganizer.visibility = View.GONE
            hsWeddingOrganizer.visibility = View.VISIBLE
        }
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
//                // Membuat objek RequestBody dari file PDF
//                val requestFile = file.toRequestBody("application/pdf".toMediaTypeOrNull())
//                // Membuat objek MultipartBody.Part untuk file PDF
//                pdfPart = MultipartBody.Part.createFormData("materi_pdf", pdfFileName, requestFile)

                pdfPart = convertFileToMultipartBody(file, pdfFileName, nameAPI)
            }
        }
        return pdfPart
    }

    private fun convertFileToMultipartBody(file: ByteArray, pdfFileName: String, nameAPI:String): MultipartBody.Part{
        val requestFile = file.toRequestBody("application/pdf".toMediaTypeOrNull())

        return  MultipartBody.Part.createFormData(nameAPI, pdfFileName, requestFile)
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

            dialog!!.etEditLogo.text = nameImage

            // Mengirim file PDF ke website menggunakan Retrofit
            fileImage = uploadImageToStorage(fileUri, nameImage, "gambar")
        }
    }

    private fun convertStringToMultipartBody(data: String): RequestBody {
        return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), data)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@AdminWeddingOrganizerActivity, WeddingOrganizerMainActivity::class.java))
        finish()
    }
}