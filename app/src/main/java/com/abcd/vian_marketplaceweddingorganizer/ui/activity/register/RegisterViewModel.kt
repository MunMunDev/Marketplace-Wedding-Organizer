package com.abcd.vian_marketplaceweddingorganizer.ui.activity.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.vian_marketplaceweddingorganizer.data.database.api.ApiService
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {
    private var _registerUser = MutableLiveData<UIState<ArrayList<ResponseModel>>>()

    fun postRegisterUser(nama:String, alamat:String, nomorHp:String, username:String, password:String, sebagai:String){
        viewModelScope.launch(Dispatchers.IO) {
            _registerUser.postValue(UIState.Loading)
            delay(1_000)
            try {
                val registerUser = api.addUser("", nama, alamat, nomorHp, username, password, sebagai)
                _registerUser.postValue(UIState.Success(registerUser))
            } catch (ex: Exception){
                _registerUser.postValue(UIState.Failure("Gagal : ${ex.message}"))
            }
        }
    }

    fun postRegisterWo(
        post: RequestBody, nama: RequestBody, alamat: RequestBody,
        nomorHp: RequestBody, username: RequestBody, password: RequestBody,
        sebagai: RequestBody, deskripsi: RequestBody, gambar: MultipartBody.Part
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _registerUser.postValue(UIState.Loading)
            delay(1_000)
            try {
                val registerUser = api.addWo(post, nama, alamat, nomorHp, username, password, sebagai, deskripsi, gambar)
                _registerUser.postValue(UIState.Success(registerUser))
            } catch (ex: Exception){
                _registerUser.postValue(UIState.Failure("Gagal : ${ex.message}"))
            }
        }
    }

    fun getRegisterUser(): LiveData<UIState<ArrayList<ResponseModel>>> = _registerUser
}