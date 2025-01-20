package com.abcd.vian_marketplaceweddingorganizer.ui.activity.admin.account.user

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
class AdminUserViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {
    private var _user = MutableLiveData<UIState<ArrayList<UsersModel>>>()
    private var _postTambahUser = MutableLiveData<UIState<ArrayList<ResponseModel>>>()
    var _responsePostUpdateUser = MutableLiveData<UIState<ArrayList<ResponseModel>>>()

    fun fetchUser() {
        viewModelScope.launch(Dispatchers.IO) {
            _user.postValue(UIState.Loading)
            delay(1_000)
            try {
                val fetchUser = api.getAllUser("")
                _user.postValue(UIState.Success(fetchUser))
            } catch (ex: Exception) {
                _user.postValue(UIState.Failure("Gagal : ${ex.message}"))
            }
        }
    }

    fun postTambahUser(
        nama: String, alamat: String, nomorHp: String,
        username: String, password: String
    ){
        viewModelScope.launch(Dispatchers.IO){
            _postTambahUser.postValue(UIState.Loading)
            try {
                val data = api.addUser("", nama, alamat, nomorHp, username, password, "user")
                _postTambahUser.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _postTambahUser.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun postUpdateUser(
        idUser: Int, nama: String, alamat: String,
        nomorHp: String, username: String, password: String,
        usernameLama: String
    ){
        viewModelScope.launch(Dispatchers.IO){
            _responsePostUpdateUser.postValue(UIState.Loading)
            try {
                val data = api.postUpdateAdminAkunUser("", idUser, nama, alamat, nomorHp, username, password, usernameLama)
                _responsePostUpdateUser.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _responsePostUpdateUser.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun getUser(): LiveData<UIState<ArrayList<UsersModel>>> = _user
    fun getTambahUser(): LiveData<UIState<ArrayList<ResponseModel>>> = _postTambahUser
    fun getUpdateUser(): LiveData<UIState<ArrayList<ResponseModel>>> = _responsePostUpdateUser

}