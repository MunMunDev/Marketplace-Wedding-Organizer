package com.abcd.vian_marketplaceweddingorganizer.ui.activity.admin.account.wedding_organizer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.vian_marketplaceweddingorganizer.data.database.api.ApiService
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.UsersModel
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AdminWeddingOrganizerViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {

    private var _weddingOrganizer = MutableLiveData<UIState<ArrayList<UsersModel>>>()
    private var _postTambahWeddingOrganizer = MutableLiveData<UIState<ArrayList<ResponseModel>>>()
    var _responsePostUpdateWeddingOrganizer = MutableLiveData<UIState<ArrayList<ResponseModel>>>()

    fun fetchWeddingOrganizer() {
        viewModelScope.launch(Dispatchers.IO) {
            _weddingOrganizer.postValue(UIState.Loading)
            delay(1_000)
            try {
                val fetchWeddingOrganizer = api.getAkunWeddingOrganizer("")
                _weddingOrganizer.postValue(UIState.Success(fetchWeddingOrganizer))
            } catch (ex: Exception) {
                _weddingOrganizer.postValue(UIState.Failure("Gagal : ${ex.message}"))
            }
        }
    }

    fun postTambahWoWithImage(
        post: RequestBody, nama: RequestBody, alamat: RequestBody,
        nomorHp: RequestBody, username: RequestBody, password: RequestBody,
        sebagai: RequestBody, deskripsi: RequestBody, gambar: MultipartBody.Part
    ){
        viewModelScope.launch(Dispatchers.IO){
            _postTambahWeddingOrganizer.postValue(UIState.Loading)
            try {
                val data = api.postTambahAkunWoWithImage(post, nama, alamat, nomorHp, username, password,sebagai, deskripsi, gambar)
                _postTambahWeddingOrganizer.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _postTambahWeddingOrganizer.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun postUpdateWo(
        idUser: Int, idWo: Int, nama: String, alamat: String,
        nomorHp: String, username: String, password: String,
        sebagai: String, usernameLama: String, deskripsi: String
    ){
        viewModelScope.launch(Dispatchers.IO){
            _responsePostUpdateWeddingOrganizer.postValue(UIState.Loading)
            try {
                val data = api.postUpdateAdminAkunWo("", idUser, idWo, nama, alamat, nomorHp, username, password, usernameLama, sebagai, deskripsi)
                _responsePostUpdateWeddingOrganizer.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _responsePostUpdateWeddingOrganizer.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun postUpdateWoWithImage(
        post: RequestBody, idUser: RequestBody, idWo: RequestBody, nama: RequestBody, alamat: RequestBody,
        nomorHp: RequestBody, username: RequestBody, password: RequestBody,
        sebagai: RequestBody, usernameLama: RequestBody, deskripsi: RequestBody, gambar: MultipartBody.Part
    ){
        viewModelScope.launch(Dispatchers.IO){
            _responsePostUpdateWeddingOrganizer.postValue(UIState.Loading)
            try {
                val data = api.postUpdateAdminAkunWoWithImage(post, idUser, idWo, nama, alamat, nomorHp, username, password, usernameLama, sebagai, deskripsi, gambar)
                _responsePostUpdateWeddingOrganizer.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _responsePostUpdateWeddingOrganizer.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }


    fun getWeddingOrganizer(): LiveData<UIState<ArrayList<UsersModel>>> = _weddingOrganizer
    fun getTambahWeddingOrganizer(): LiveData<UIState<ArrayList<ResponseModel>>> = _postTambahWeddingOrganizer
    fun getUpdateWeddingOrganizer(): LiveData<UIState<ArrayList<ResponseModel>>> = _responsePostUpdateWeddingOrganizer

}