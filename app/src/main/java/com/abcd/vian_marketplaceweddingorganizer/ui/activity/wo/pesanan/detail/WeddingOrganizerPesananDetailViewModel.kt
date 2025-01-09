package com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.pesanan.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.vian_marketplaceweddingorganizer.data.database.api.ApiService
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.RiwayatPesananModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.TestimoniModel
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeddingOrganizerPesananDetailViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {
    private val _detailRiwayatPesanan = MutableLiveData<UIState<ArrayList<RiwayatPesananModel>>>()
    private val _postKonfirmasiPembayaran = MutableLiveData<UIState<ArrayList<ResponseModel>>>()

    fun fetchDetailRiwayatPesanan(idPemesanan: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _detailRiwayatPesanan.postValue(UIState.Loading)
            delay(1_000)
            try {
                val dataPembayaran = api.getWeddingOrganizerRiwayatPesananDetail("", idPemesanan)
                _detailRiwayatPesanan.postValue(UIState.Success(dataPembayaran))
            } catch (ex: Exception) {
                _detailRiwayatPesanan.postValue(UIState.Failure("Error pada: ${ex.message}"))
            }
        }
    }


    fun postKonfirmasiPembayaran(
        idPemesanan: Int
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _postKonfirmasiPembayaran.postValue(UIState.Loading)
            delay(1_000)
            try {
                val postTambahTestimoni = api.postWeddingOrganizerKonfirmasiPembayaranSelesai("", idPemesanan)
                _postKonfirmasiPembayaran.postValue(UIState.Success(postTambahTestimoni))
            } catch (ex: Exception){
                _postKonfirmasiPembayaran.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun getDetailRiwayatPesanan(): LiveData<UIState<ArrayList<RiwayatPesananModel>>> = _detailRiwayatPesanan
    fun getResponseKonfirmasiPembayaran(): LiveData<UIState<ArrayList<ResponseModel>>> = _postKonfirmasiPembayaran

}