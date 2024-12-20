package com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.wedding_organizer.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.vian_marketplaceweddingorganizer.data.database.api.ApiService
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.TestimoniModel
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeddingOrganizerDetailViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {
    private var _fetchTestimoni = MutableLiveData<UIState<ArrayList<TestimoniModel>>>()
    private var _postTambahPesanan = MutableLiveData<UIState<ArrayList<ResponseModel>>>()
    fun fetchTestimoni(
        idWeddingOrganizer: Int
    ){
        viewModelScope.launch {
            _fetchTestimoni.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = api.getTestimoni(
                    "", idWeddingOrganizer
                )
                _fetchTestimoni.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _fetchTestimoni.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun postTambahPesanan(
        idWeddingOrganizer: String, idUser: String, jumlah: String
    ){
        viewModelScope.launch {
            _postTambahPesanan.postValue(UIState.Loading)
            delay(1_000)
            try {
                val postTambahWeddingOrganizer = api.postTambahPesanan(
                    "", idWeddingOrganizer, idUser, jumlah
                )
                _postTambahPesanan.postValue(UIState.Success(postTambahWeddingOrganizer))
            } catch (ex: Exception){
                _postTambahPesanan.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun getTestimoni(): LiveData<UIState<ArrayList<TestimoniModel>>> = _fetchTestimoni
    fun getTambahPesanan(): LiveData<UIState<ArrayList<ResponseModel>>> = _postTambahPesanan
}