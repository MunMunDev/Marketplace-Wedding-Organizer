package com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.akun

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
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.UsersModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityAkunBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityWeddingOrganizerAkunBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogAkunBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogAkunWoBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogShowImageBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.register.RegisterActivity
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.akun.AkunViewModel
import com.abcd.vian_marketplaceweddingorganizer.utils.Constant
import com.abcd.vian_marketplaceweddingorganizer.utils.KontrolNavigationDrawer
import com.abcd.vian_marketplaceweddingorganizer.utils.LoadingAlertDialog
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@AndroidEntryPoint
class WeddingOrganizerAkunActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWeddingOrganizerAkunBinding
    private lateinit var kontrolNavigationDrawer: KontrolNavigationDrawer
    private lateinit var sharedPreferences: SharedPreferencesLogin
    private val viewModel: WeddingOrganizerAkunViewModel by viewModels()
    @Inject
    lateinit var loading: LoadingAlertDialog
    private var tempUser: UsersModel = UsersModel()
    private var fileImage: MultipartBody.Part? = null
    private var dialog: AlertDialogAkunWoBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeddingOrganizerAkunBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSharedPreferences()
        setKontrolNavigationDrawer()
        setData()
        button()
        getPostUpdateData()
    }

    private fun setSharedPreferences() {
        sharedPreferences = SharedPreferencesLogin(this@WeddingOrganizerAkunActivity)
    }

    private fun setKontrolNavigationDrawer() {
        binding.apply {
            kontrolNavigationDrawer = KontrolNavigationDrawer(this@WeddingOrganizerAkunActivity)
            kontrolNavigationDrawer.cekSebagai(navView)
            kontrolNavigationDrawer.onClickItemNavigationDrawer(navView, drawerLayoutMain, ivDrawerView, this@WeddingOrganizerAkunActivity)
        }
    }

    private fun setData(){
        binding.apply {
            etNama.text = sharedPreferences.getNama()
            etNomorHp.text = sharedPreferences.getNomorHp()
            etAlamat.text = sharedPreferences.getAlamat()
            etUsername.text = sharedPreferences.getUsername()
            etPassword.text = sharedPreferences.getPassword()
            etDeskripsi.text = sharedPreferences.getDeskripsi()

            Glide.with(ivLogo)
                .load("${Constant.BASE_URL}${Constant.LOCATION_GAMBAR}${sharedPreferences.getLogo()}") // URL Gambar
                .error(R.drawable.background_main2)
                .placeholder(R.drawable.loading)
                .into(ivLogo) // imageView mana yang akan diterapkan

            ivLogo.setOnClickListener {
                setShowImage("Logo Akun", "${Constant.BASE_URL}${Constant.LOCATION_GAMBAR}${sharedPreferences.getLogo()}")
            }
        }
    }

    private fun setShowImage(deskripsiLogo: String, image: String) {
        val view = AlertDialogShowImageBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@WeddingOrganizerAkunActivity)
        alertDialog.setView(view.root)
            .setCancelable(false)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            tvTitle.text = deskripsiLogo
            btnClose.setOnClickListener {
                dialogInputan.dismiss()
            }
        }

        Glide.with(this@WeddingOrganizerAkunActivity)
            .load(image) // URL Gambar
            .error(R.drawable.background_main2)
            .placeholder(R.drawable.loading)
            .into(view.ivShowImage) // imageView mana yang akan diterapkan

    }

    private fun button() {
        binding.btnUbahData.setOnClickListener {
            setDialogUpdateData()
        }
    }

    private fun setDialogUpdateData() {
        val view = AlertDialogAkunWoBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(this@WeddingOrganizerAkunActivity)
        alertDialog.setView(view.root)
            .setCancelable(false)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        dialog = view

        view.apply {
            etEditNama.setText(sharedPreferences.getNama())
            etEditAlamat.setText(sharedPreferences.getAlamat())
            etEditNomorHp.setText(sharedPreferences.getNomorHp())
            etEditUsername.setText(sharedPreferences.getUsername())
            etEditPassword.setText(sharedPreferences.getPassword())
            etEditDeskripsi.setText(sharedPreferences.getDeskripsi())

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
                if(etEditNama.toString().isEmpty()){
                    etEditNama.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditNomorHp.toString().isEmpty()){
                    etEditNomorHp.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditUsername.toString().isEmpty()){
                    etEditUsername.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditPassword.toString().isEmpty()){
                    etEditPassword.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditDeskripsi.toString().isEmpty()){
                    etEditDeskripsi.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditLogo.text.toString() == resources.getString(R.string.ket_klik_file)){
                    checkLogo = true
                }

                if(!cek){
                    if(checkLogo){
                        tempUser = UsersModel(
                            sharedPreferences.getIdUser(),
                            sharedPreferences.getIdWO(),
                            etEditNama.text.toString(),
                            etEditNomorHp.text.toString(),
                            etEditAlamat.text.toString(),
                            etEditUsername.text.toString(),
                            etEditPassword.text.toString(),
                            "wo",
                            etEditDeskripsi.text.toString(),
                            sharedPreferences.getLogo()
                        )
                        postUpdateDataWo(
                            sharedPreferences.getIdUser(),
                            sharedPreferences.getIdWO(),
                            etEditNama.text.toString(),
                            etEditAlamat.text.toString(),
                            etEditNomorHp.text.toString(),
                            etEditUsername.text.toString(),
                            etEditPassword.text.toString(),
                            sharedPreferences.getUsername(),
                            etEditDeskripsi.text.toString()
                        )
                    } else{
                        tempUser = UsersModel(
                            sharedPreferences.getIdUser(),
                            sharedPreferences.getIdWO(),
                            etEditNama.text.toString(),
                            etEditNomorHp.text.toString(),
                            etEditAlamat.text.toString(),
                            etEditUsername.text.toString(),
                            etEditPassword.text.toString(),
                            "wo",
                            etEditDeskripsi.text.toString(),
                            sharedPreferences.getLogo()
                        )
                        postUpdateDataWoWithImage(
                            sharedPreferences.getIdUser().toString(),
                            sharedPreferences.getIdWO().toString(),
                            etEditNama.text.toString(),
                            etEditAlamat.text.toString(),
                            etEditNomorHp.text.toString(),
                            etEditUsername.text.toString(),
                            etEditPassword.text.toString(),
                            sharedPreferences.getUsername(),
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
            convertStringToMultipartBody(""),
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
        viewModel.getUpdateData().observe(this@WeddingOrganizerAkunActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@WeddingOrganizerAkunActivity)
                is UIState.Success-> setSuccessUpdateData(result.data)
                is UIState.Failure-> setFailureUpdateData(result.message)
            }
        }
    }

    private fun setFailureUpdateData(message: String) {
        Toast.makeText(this@WeddingOrganizerAkunActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setSuccessUpdateData(data: ArrayList<ResponseModel>) {
        if(data.isNotEmpty()){
            if(data[0].status == "0"){
                Toast.makeText(this@WeddingOrganizerAkunActivity, "Berhasil Update Akun", Toast.LENGTH_SHORT).show()
                sharedPreferences.setLogin(
                    tempUser.idUser!!,
                    tempUser.id_wo!!,
                    tempUser.nama!!,
                    tempUser.nomorHp!!,
                    tempUser.alamat!!,
                    tempUser.username!!,
                    tempUser.password!!,
                    "wo",
                    tempUser.deskripsi_wo!!,
                    tempUser.logo_wo!!
                )
                tempUser = UsersModel()
                setData()
            } else{
                Toast.makeText(this@WeddingOrganizerAkunActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@WeddingOrganizerAkunActivity, "Gagal: Ada kesalahan di API", Toast.LENGTH_SHORT).show()
        }
        loading.alertDialogCancel()
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

    private fun convertFileToMultipartBody(file: ByteArray, pdfFileName: String, nameAPI:String): MultipartBody.Part?{
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
}