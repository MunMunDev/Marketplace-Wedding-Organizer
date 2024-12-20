package com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.chat.chat

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
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.vian_marketplaceweddingorganizer.adapter.ChatWeddingOrganizerAdapter
import com.abcd.vian_marketplaceweddingorganizer.data.model.MessageModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.databinding.ActivityChatWeddingOrganizerBinding
import com.abcd.vian_marketplaceweddingorganizer.databinding.AlertDialogHapusBinding
import com.abcd.vian_marketplaceweddingorganizer.utils.OnClickItem
import com.abcd.vian_marketplaceweddingorganizer.utils.SharedPreferencesLogin
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.Date
import java.util.TimeZone

@AndroidEntryPoint
class ChatWeddingOrganizerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatWeddingOrganizerBinding
    private val viewModel: ChatWeddingOrganizerViewModel by viewModels()
    private lateinit var sharedPref: SharedPreferencesLogin
    private lateinit var messageAdapter: ChatWeddingOrganizerAdapter
    private var messageArrayList : ArrayList<MessageModel> = arrayListOf()
    var idSent: Int? = null
    var idReceived: Int? = null
    var senderRoom: String? = null
    var receivedRoom: String? = null
    var namaWeddingOrganizer: String? = null
    val TAG = "DetailTag";
    var add: Boolean = true

    var image = ""

    val STORAGE_PERMISSION_CODE = 10
    val IMAGE_CODE = 11
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatWeddingOrganizerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        fetchDataSebelumnya()
        setButton()
        setAdapterChatWeddingOrganizer(messageArrayList)
        getResponseMessage()
        getChatWeddingOrganizer()
        getMessageImage()
        getDeleteMessage()
        updateMessageInFiveSeconds()
    }
    private fun fetchDataSebelumnya() {
        binding.apply {
            val bundle = intent.extras
            sharedPref = SharedPreferencesLogin(this@ChatWeddingOrganizerActivity)
            idSent = sharedPref.getIdUser()
            bundle!!.apply {
                idReceived = this.getInt("id_received", 0)
                namaWeddingOrganizer = this.getString("nama_wedding_organizer").toString()

                fetchChatWeddingOrganizer(sharedPref.getIdUser(), idReceived!!)

            }

            appNavbarDrawer.apply {
                tvTitle.text = namaWeddingOrganizer
                ivNavDrawer.visibility = View.GONE
                ivBack.visibility = View.VISIBLE
            }
        }
    }
    private fun setButton() {
        binding.apply {
            appNavbarDrawer.ivBack.setOnClickListener{
                finish()
            }

            btnUploadCamera.setOnClickListener {
                if(checkPermission()){
                    pickImageFile()
                } else{
                    requestPermission()
                }
            }

            btnSendMessage.setOnClickListener {
                if(etMessage.text.trim().isNotEmpty()){
                    // Kirim data
                    if(sharedPref.getSebagai() == "user"){
                        // user
                        postMessage(sharedPref.getIdUser(), idReceived!!, etMessage.text.toString().trim(), "user")
                    } else{
                        // wo
                        postMessage(idReceived!!, sharedPref.getIdUser(), etMessage.text.toString().trim(), "wo")
                    }
                }
            }
        }
    }

    private fun postMessage(
        idUser: Int,
        idWo: Int,
        message: String,
        pengirim: String,
    ){
        viewModel.postMessage(message, idUser, idWo, pengirim)
    }

    private fun getResponseMessage(){
        viewModel.getMessage().observe(this@ChatWeddingOrganizerActivity){result->
            when(result){
                is UIState.Loading->{}
                is UIState.Success-> setSuccessPostMessage(result.data)
                is UIState.Failure-> setFailurePostMessage(result.message)
            }
        }
    }

    private fun setFailurePostMessage(message: String) {
        Toast.makeText(this@ChatWeddingOrganizerActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessPostMessage(data: ArrayList<ResponseModel>) {
        if(data.isNotEmpty()){
            if(data[0].status == "0"){
                binding.apply {
                    etMessage.setText("")
                    add = true
                    fetchChatWeddingOrganizer(sharedPref.getIdUser(), idReceived!!)
                }
            } else{
                Toast.makeText(this@ChatWeddingOrganizerActivity, "Gagal", Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@ChatWeddingOrganizerActivity, "Terjadi error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchChatWeddingOrganizer(idUser:Int, idReceived:Int){
        viewModel.fetchChatWeddingOrganizer(idUser, idReceived)
    }

    private fun getChatWeddingOrganizer(){
        viewModel.getChatWeddingOrganizer().observe(this@ChatWeddingOrganizerActivity){result->
            when(result){
                is UIState.Loading->{}
                is UIState.Success-> setSuccessFetchChatWeddingOrganizer(result.data)
                is UIState.Failure-> setFailureFetchChatWeddingOrganizer(result.message)
            }
        }
    }

    private fun setFailureFetchChatWeddingOrganizer(message: String) {
        Toast.makeText(this@ChatWeddingOrganizerActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessFetchChatWeddingOrganizer(data: ArrayList<MessageModel>) {
//        setAdapterChatWeddingOrganizer(data)
        messageAdapter.updateMessage(data, add, binding.rvListKonsultasiChatDokter)
//        binding.rvListKonsultasiChatDokter.scrollToPosition(data.size-1)
    }

    private fun setAdapterChatWeddingOrganizer(list: ArrayList<MessageModel>) {
        messageAdapter = ChatWeddingOrganizerAdapter(
            this@ChatWeddingOrganizerActivity,
            list,
            sharedPref.getIdUser(),
            object: OnClickItem.ClickChatWeddingOrganizer{
            override fun clickItemHapus(message: MessageModel) {
                val view = AlertDialogHapusBinding.inflate(layoutInflater)
                val alertDialog = AlertDialog.Builder(this@ChatWeddingOrganizerActivity)
                alertDialog.setView(view.root)
                val dialog = alertDialog.create()
                dialog.show()

                view.apply {
                    btnHapus.setOnClickListener {
                        postDeleteMessage(message.idMessage!!)
                        dialog.dismiss()
                    }

                    btnBatalHapus.setOnClickListener {
                        dialog.dismiss()
                    }
                }
            }

            override fun clickItemGambarHapus(message: MessageModel) {
                val view = AlertDialogHapusBinding.inflate(layoutInflater)
                val alertDialog = AlertDialog.Builder(this@ChatWeddingOrganizerActivity)
                alertDialog.setView(view.root)
                val dialog = alertDialog.create()
                dialog.show()

                view.tvTitle.text = "Hapus Gambar ?"

                view.apply {
                    btnHapus.setOnClickListener {
                        postDeleteMessage(message.idMessage!!)
                        dialog.dismiss()
                    }

                    btnBatalHapus.setOnClickListener {
                        dialog.dismiss()
                    }
                }
            }

        })

        binding.apply {
            rvListKonsultasiChatDokter.layoutManager = LinearLayoutManager(this@ChatWeddingOrganizerActivity)
            rvListKonsultasiChatDokter.adapter = messageAdapter

            messageAdapter.notifyDataSetChanged()
        }
    }

    fun idMessageImageUser(): String{
        val dateTime = "${tanggalSekarangZonaMakassar()}-${waktuSekarangZonaMakassar()}"
        val idMessage = "${sharedPref.getIdUser()}-user-${idReceived}-$dateTime"

        return idMessage
    }

    fun idMessageImageWo(): String{
        val dateTime = "${tanggalSekarangZonaMakassar()}-${waktuSekarangZonaMakassar()}"
        val idMessage = "${sharedPref.getIdUser()}-wo-${idReceived}-$dateTime"

        return idMessage
    }

    // permission add image
    private fun requestPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            if (Environment.isExternalStorageManager()) {
                startActivity(Intent(this@ChatWeddingOrganizerActivity, ChatWeddingOrganizerActivity::class.java))
            } else { //request for the permission
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
        } else{
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_CODE
            )
        }
    }

    private fun pickImageFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "image/*"
        }

        startActivityForResult(intent, IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_CODE && resultCode == Activity.RESULT_OK && data != null) {
            // Mendapatkan URI file PDF yang dipilih
            val fileUri = data.data!!

            val nameImage = getNameFile(fileUri)

            image = nameImage

            val arrayNamaGambar = nameImage.split(".")
            val sizeArrayGambar = arrayNamaGambar.size
            val ekstensi = arrayNamaGambar[sizeArrayGambar-1].trim()

            var post = convertStringToMultipartBody("post_message_image")
            var fileImage = uploadImageToStorage(fileUri, nameImage, "gambar")
            var idUser = convertStringToMultipartBody(sharedPref.getIdUser().toString())
            var idReceivedTemp = convertStringToMultipartBody(idReceived.toString())

            if(sharedPref.getSebagai() == "user"){
                val fileName = "${idMessageImageUser()}.$ekstensi"
                Log.d(TAG, "file: $fileImage")
                Log.d(TAG, "name: $fileName")
                postMessageImage(post, fileImage!!, fileName, idUser, idReceivedTemp, "user")
            }else{
                val fileName = "${idMessageImageWo()}.$ekstensi"
                postMessageImage(post, fileImage!!, fileName, idReceivedTemp, idUser, "wo")
            }
        }
    }

    private fun postMessageImage(
        post: RequestBody,
        fileImage: MultipartBody.Part,
        fileName: String,
        idUser: RequestBody,
        idReceived: RequestBody,
        pengirim: String,

    ) {
        val namaImage = convertStringToMultipartBody(fileName)
        val pengirimTemp = convertStringToMultipartBody(pengirim)

        viewModel.postMessageImage(
            post, fileImage, namaImage, idUser, idReceived, pengirimTemp
        )
    }

    private fun getMessageImage(){
        viewModel.getPostMessageImage().observe(this@ChatWeddingOrganizerActivity){result->
            when(result){
                is UIState.Loading-> {}
                is UIState.Success-> setSuccessPostMessageImage(result.data)
                is UIState.Failure-> setFailurePostMessageImage(result.message)
            }
        }
    }

    private fun setFailurePostMessageImage(message: String) {
        Toast.makeText(this@ChatWeddingOrganizerActivity, message, Toast.LENGTH_SHORT).show()
        Log.d(TAG, "$message ")
    }

    private fun setSuccessPostMessageImage(data: ArrayList<ResponseModel>) {
        if(data.isNotEmpty()){
            if(data[0].status == "0"){
                Toast.makeText(this@ChatWeddingOrganizerActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                add = true
                fetchChatWeddingOrganizer(sharedPref.getIdUser(), idReceived!!)
            } else{
                Toast.makeText(this@ChatWeddingOrganizerActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@ChatWeddingOrganizerActivity, "Error di web", Toast.LENGTH_SHORT).show()
        }
    }

    private fun postDeleteMessage(idMessage: Int){
        viewModel.postDeleteMessage(idMessage)
    }

    private fun getDeleteMessage(){
        viewModel.getPostDeleteMessage().observe(this@ChatWeddingOrganizerActivity){result->
            when(result){
                is UIState.Loading->{}
                is UIState.Success-> setSuccessPostDeleteMessage(result.data)
                is UIState.Failure-> setFailurePostDeleteMessage(result.message)
            }
        }
    }

    private fun setFailurePostDeleteMessage(message: String) {
        Toast.makeText(this@ChatWeddingOrganizerActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessPostDeleteMessage(data: ArrayList<ResponseModel>) {
        if(data.isNotEmpty()){
            if(data[0].status=="0"){
                Toast.makeText(this@ChatWeddingOrganizerActivity, "Berhasil Hapus", Toast.LENGTH_SHORT).show()
                add = false
                fetchChatWeddingOrganizer(sharedPref.getIdUser(), idReceived!!)
            } else{
                Toast.makeText(this@ChatWeddingOrganizerActivity, data[0].message_response, Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this@ChatWeddingOrganizerActivity, "Ada error di web", Toast.LENGTH_SHORT).show()
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
    private fun uploadImageToStorage(imageUri: Uri?, fileName: String, nameAPI:String): MultipartBody.Part? {
        var imagePart : MultipartBody.Part? = null
        imageUri?.let {
            val file = contentResolver.openInputStream(imageUri)?.readBytes()

            if (file != null) {
                imagePart = convertFileToMultipartBody(file, fileName, nameAPI)
            }
        }
        return imagePart
    }

    private fun convertFileToMultipartBody(file: ByteArray, fileName: String, nameAPI:String): MultipartBody.Part?{
//        val requestFile = file.toRequestBody("application/pdf".toMediaTypeOrNull())
        val requestFile = file.toRequestBody("application/image".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData(nameAPI, fileName, requestFile)

        return filePart
    }

    private fun convertStringToMultipartBody(data: String): RequestBody {
        return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), data)
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

    @SuppressLint("SimpleDateFormat")
    fun tanggalSekarangZonaMakassar():String{
        var date = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val makassarZone  = ZoneId.of("Asia/Makassar")
            val makassarTanggal = LocalDate.now(makassarZone)
            var arrayTanggal = "$makassarTanggal".split("-")
            val tanggal = "${arrayTanggal[0]}${arrayTanggal[1]}${arrayTanggal[2]}"
            date = "$tanggal"
        } else {
            val makassarTimeZone = TimeZone.getTimeZone("Asia/Makassar")
            val dateFormat = SimpleDateFormat("yyyyMMdd")
            dateFormat.timeZone = makassarTimeZone
            val currentDate = Date()
            val makassarDate = dateFormat.format(currentDate)
            date = makassarDate
        }

        return date
    }

    @SuppressLint("SimpleDateFormat")
    fun waktuSekarangZonaMakassar():String{
        var time = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val makassarZone  = ZoneId.of("Asia/Makassar")
            val makassarTime = LocalTime.now(makassarZone)
            val tempWaktu = makassarTime.toString().split(".")

            var arrayWaktu = tempWaktu[0].split(":")
            val waktu = "${arrayWaktu[0]}${arrayWaktu[1]}${arrayWaktu[2]}"
            time = waktu

        } else {
            val makassarTimeZone = TimeZone.getTimeZone("Asia/Makassar")
            val timeFormat = SimpleDateFormat("HHmmss")
            timeFormat.timeZone = makassarTimeZone
            val currentTime = Date()
            val makassarTime = timeFormat.format(currentTime)
            time = makassarTime
        }
        return time
    }

    private fun updateMessageInFiveSeconds(){
//        try {
//            Handler().postDelayed({
//                Toast.makeText(this@ChatWeddingOrganizerActivity, "update", Toast.LENGTH_SHORT).show()
//                fetchChatWeddingOrganizer(sharedPref.getIdUser(), idReceived!!)
//                updateMessageInFiveSeconds()
//            }, 10_000)
//        } catch (ex: Exception){
//            updateMessageInFiveSeconds()
//        }
    }

}