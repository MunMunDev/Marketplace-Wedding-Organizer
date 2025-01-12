package com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.akun

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.vian_marketplaceweddingorganizer.data.database.api.ApiService
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class WeddingOrganizerAkunViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {
    var _responsePostUpdateUser = MutableLiveData<UIState<ArrayList<ResponseModel>>>()

    fun postUpdateWo(
        idUser: Int, idWo: Int, nama: String, alamat: String,
        nomorHp: String, username: String, password: String,
        sebagai: String, usernameLama: String, deskripsi: String
    ){
        viewModelScope.launch(Dispatchers.IO){
            _responsePostUpdateUser.postValue(UIState.Loading)
            try {
                val data = api.postUpdateAkunWo("", idUser, idWo, nama, alamat, nomorHp, username, password, usernameLama, sebagai, deskripsi)
                _responsePostUpdateUser.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _responsePostUpdateUser.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun postUpdateWoWithImage(
        post: RequestBody, idUser: RequestBody, idWo: RequestBody, nama: RequestBody, alamat: RequestBody,
        nomorHp: RequestBody, username: RequestBody, password: RequestBody,
        sebagai: RequestBody, usernameLama: RequestBody, deskripsi: RequestBody, gambar: MultipartBody.Part
    ){
        viewModelScope.launch(Dispatchers.IO){
            _responsePostUpdateUser.postValue(UIState.Loading)
            try {
                val data = api.postUpdateAkunWoWithImage(post, idUser, idWo, nama, alamat, nomorHp, username, password, usernameLama, sebagai, deskripsi, gambar)
                _responsePostUpdateUser.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _responsePostUpdateUser.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun getUpdateData(): LiveData<UIState<ArrayList<ResponseModel>>> = _responsePostUpdateUser
}