package com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.vendor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.vian_marketplaceweddingorganizer.data.database.api.ApiService
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.VendorModel
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeddingOrganizerVendorViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {

    private var _vendor = MutableLiveData<UIState<ArrayList<VendorModel>>>()
    private var _postTambahVendor = MutableLiveData<UIState<ArrayList<ResponseModel>>>()
    private var _postUpdateVendor = MutableLiveData<UIState<ArrayList<ResponseModel>>>()
    private var _postDeleteVendor = MutableLiveData<UIState<ArrayList<ResponseModel>>>()

    fun fetchVendor(id_wo: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _vendor.postValue(UIState.Loading)
            delay(1_000)
            try {
                val fetchVendor = api.getWeddingOrganizerVendor("", id_wo)
                _vendor.postValue(UIState.Success(fetchVendor))
            } catch (ex: Exception) {
                _vendor.postValue(UIState.Failure("Gagal : ${ex.message}"))
            }
        }
    }

    fun postTambahVendor(id_wo: Int, vendor: String, harga: Int) {
        viewModelScope.launch {
            _postTambahVendor.postValue(UIState.Loading)
            delay(1_000)
            try {
                val postTambahVendor = api.postTambahVendor(
                    "", id_wo, vendor, harga
                )
                _postTambahVendor.postValue(UIState.Success(postTambahVendor))
            } catch (ex: Exception) {
                _postTambahVendor.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun postUpdateVendor(
        idVendor: Int, vendor: String, harga: Int
    ) {
        viewModelScope.launch {
            _postUpdateVendor.postValue(UIState.Loading)
            delay(1_000)
            try {
                val postTambahVendor = api.postUpdateVendor(
                    "", idVendor, vendor, harga
                )
                _postUpdateVendor.postValue(UIState.Success(postTambahVendor))
            } catch (ex: Exception) {
                _postUpdateVendor.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun postDeleteVendor(idVendor: Int) {
        viewModelScope.launch {
            _postDeleteVendor.postValue(UIState.Loading)
            delay(1_000)
            try {
                val postTambahVendor = api.postDeleteVendor(
                    "", idVendor
                )
                _postDeleteVendor.postValue(UIState.Success(postTambahVendor))
            } catch (ex: Exception) {
                _postDeleteVendor.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun getVendor(): LiveData<UIState<ArrayList<VendorModel>>> = _vendor
    fun getTambahVendor(): LiveData<UIState<ArrayList<ResponseModel>>> = _postTambahVendor
    fun getUpdateVendor(): LiveData<UIState<ArrayList<ResponseModel>>> = _postUpdateVendor
    fun getDeleteVendor(): LiveData<UIState<ArrayList<ResponseModel>>> = _postDeleteVendor
}