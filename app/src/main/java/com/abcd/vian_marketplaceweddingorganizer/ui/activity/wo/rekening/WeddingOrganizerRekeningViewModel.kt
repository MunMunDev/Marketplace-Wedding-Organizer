package com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.rekening

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.vian_marketplaceweddingorganizer.data.database.api.ApiService
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.RekeningModel
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeddingOrganizerRekeningViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {

    private var _vendor = MutableLiveData<UIState<ArrayList<RekeningModel>>>()
    private var _postTambahRekening = MutableLiveData<UIState<ArrayList<ResponseModel>>>()
    private var _postUpdateRekening = MutableLiveData<UIState<ArrayList<ResponseModel>>>()
    private var _postDeleteRekening = MutableLiveData<UIState<ArrayList<ResponseModel>>>()

    fun fetchRekening(id_wo: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _vendor.postValue(UIState.Loading)
            delay(1_000)
            try {
                val fetchRekening = api.getWeddingOrganizerRekening("", id_wo)
                _vendor.postValue(UIState.Success(fetchRekening))
            } catch (ex: Exception) {
                _vendor.postValue(UIState.Failure("Gagal : ${ex.message}"))
            }
        }
    }

    fun postTambahRekening(id_wo: Int, vendor: String, harga: String, nama:String) {
        viewModelScope.launch {
            _postTambahRekening.postValue(UIState.Loading)
            delay(1_000)
            try {
                val postTambahRekening = api.postTambahRekening(
                    "", id_wo, vendor, harga, nama
                )
                _postTambahRekening.postValue(UIState.Success(postTambahRekening))
            } catch (ex: Exception) {
                _postTambahRekening.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun postUpdateRekening(
        idRekening: Int, vendor: String, harga: Int, nama:String
    ) {
        viewModelScope.launch {
            _postUpdateRekening.postValue(UIState.Loading)
            delay(1_000)
            try {
                val postTambahRekening = api.postUpdateRekening(
                    "", idRekening, vendor, harga, nama
                )
                _postUpdateRekening.postValue(UIState.Success(postTambahRekening))
            } catch (ex: Exception) {
                _postUpdateRekening.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun postDeleteRekening(idRekening: Int) {
        viewModelScope.launch {
            _postDeleteRekening.postValue(UIState.Loading)
            delay(1_000)
            try {
                val postTambahRekening = api.postDeleteRekening(
                    "", idRekening
                )
                _postDeleteRekening.postValue(UIState.Success(postTambahRekening))
            } catch (ex: Exception) {
                _postDeleteRekening.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun getRekening(): LiveData<UIState<ArrayList<RekeningModel>>> = _vendor
    fun getTambahRekening(): LiveData<UIState<ArrayList<ResponseModel>>> = _postTambahRekening
    fun getUpdateRekening(): LiveData<UIState<ArrayList<ResponseModel>>> = _postUpdateRekening
    fun getDeleteRekening(): LiveData<UIState<ArrayList<ResponseModel>>> = _postDeleteRekening
}