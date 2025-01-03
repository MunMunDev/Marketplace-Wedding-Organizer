package com.abcd.vian_marketplaceweddingorganizer.ui.activity.register

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
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.abcd.vian_marketplaceweddingorganizer.R
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityRegisterBinding
import com.abcd.vian_marketplaceweddingorganizer.ui.activity.login.LoginActivity
import com.abcd.vian_marketplaceweddingorganizer.utils.Constant
import com.abcd.vian_marketplaceweddingorganizer.utils.LoadingAlertDialog
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    @Inject
    lateinit var loading: LoadingAlertDialog
    private val viewModel : RegisterViewModel by viewModels()
    private var checkRegisterUser = true
    private var fileImage: MultipartBody.Part? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButton()
        postRegisterUser()
    }
    private fun setButton() {
        binding.apply {
            tvLogin.setOnClickListener{
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }
            btnRegistrasi.setOnClickListener {
                if(checkRegisterUser){
                    buttonRegistrasi()
                } else{
                    buttonRegistrasiWo()
                }
            }
            etEditLogo.setOnClickListener {
                if(checkPermission()){
                    pickImageFile()
                } else{
                    requestPermission()
                }
            }

            setRadioButton()
        }
    }

    private fun setRadioButton() {
        binding.apply {
            rbRegisterUser.isChecked = true

            rgRegister.setOnCheckedChangeListener(object: RadioGroup.OnCheckedChangeListener{
                override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                    when(checkedId){
                        R.id.rbRegisterUser->{
                            tvDeskripsi.visibility = View.GONE
                            etEditDeskripsiWo.visibility = View.GONE
                            tvLogo.visibility = View.GONE
                            etEditLogo.visibility = View.GONE

                            checkRegisterUser = true
                        }
                        R.id.rbRegisterWo->{
                            tvDeskripsi.visibility = View.VISIBLE
                            etEditDeskripsiWo.visibility = View.VISIBLE
                            tvLogo.visibility = View.VISIBLE
                            etEditLogo.visibility = View.VISIBLE

                            checkRegisterUser = false
                        }
                    }
                }

            })
        }
    }

    private fun buttonRegistrasi() {
        binding.apply {
            var cek = false
            if (etEditNama.toString().isEmpty()) {
                etEditNama.error = "Tidak Boleh Kosong"
                cek = true
            }
            if (etEditAlamat.toString().isEmpty()) {
                etEditAlamat.error = "Tidak Boleh Kosong"
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
                postTambahData(
                    etEditNama.text.toString().trim(),
                    etEditAlamat.text.toString().trim(),
                    etEditNomorHp.text.toString().trim(),
                    etEditUsername.text.toString().trim(),
                    etEditPassword.text.toString().trim()
                )
            }
        }
    }

    private fun postTambahData(nama: String, alamat: String, nomorHp: String, username: String, password: String){
        viewModel.postRegisterUser(nama, alamat, nomorHp, username, password, "user")
    }

    private fun postRegisterUser(){
        viewModel.getRegisterUser().observe(this@RegisterActivity){ result ->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@RegisterActivity)
                is UIState.Failure-> responseFailureRegisterUser(result.message)
                is UIState.Success-> responseSuccessRegiserUser(result.data)
            }
        }
    }

    private fun responseFailureRegisterUser(message: String) {
        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun responseSuccessRegiserUser(data: ArrayList<ResponseModel>) {
        if (data.isNotEmpty()){
            if (data[0].status == "0"){
                Toast.makeText(this@RegisterActivity, "Berhasil melakukan registrasi", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this@RegisterActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this@RegisterActivity, "Maaf gagal", Toast.LENGTH_SHORT).show()
        }
        loading.alertDialogCancel()
    }

    private fun buttonRegistrasiWo() {
        binding.apply {
            var cek = false
            if (etEditNama.toString().isEmpty()) {
                etEditNama.error = "Tidak Boleh Kosong"
                cek = true
            }
            if (etEditAlamat.toString().isEmpty()) {
                etEditAlamat.error = "Tidak Boleh Kosong"
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
            if (etEditDeskripsiWo.toString().isEmpty()) {
                etEditDeskripsiWo.error = "Tidak Boleh Kosong"
                cek = true
            }
            if (etEditLogo.toString() == resources.getString(R.string.ket_klik_file)) {
                etEditLogo.error = "Tidak Boleh Kosong"
                cek = true
            }

            if (!cek) {
                postTambahDataWo(
                    etEditNama.text.toString().trim(),
                    etEditAlamat.text.toString().trim(),
                    etEditNomorHp.text.toString().trim(),
                    etEditUsername.text.toString().trim(),
                    etEditPassword.text.toString().trim(),
                    etEditDeskripsiWo.text.toString().trim()
                )
            }
        }
    }

    private fun postTambahDataWo(nama: String, alamat: String, nomorHp: String, username: String, password: String, deskripsi: String){
        viewModel.postRegisterWo(
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

            binding.etEditLogo.text = nameImage

            // Mengirim file PDF ke website menggunakan Retrofit
            fileImage = uploadImageToStorage(fileUri, nameImage, "gambar")
        }
    }

    private fun convertStringToMultipartBody(data: String): RequestBody {
        return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), data)
    }
}