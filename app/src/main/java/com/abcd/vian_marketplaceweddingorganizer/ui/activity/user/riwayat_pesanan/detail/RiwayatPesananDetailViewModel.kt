package com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.riwayat_pesanan.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.vian_marketplaceweddingorganizer.data.database.api.ApiService
import com.abcd.vian_marketplaceweddingorganizer.data.model.RekeningModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.RiwayatPesananModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.TestimoniModel
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class RiwayatPesananDetailViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {
    private var _fetchTestimoni = MutableLiveData<UIState<ArrayList<TestimoniModel>>>()
    val _detailRiwayatPesanan = MutableLiveData<UIState<ArrayList<RiwayatPesananModel>>>()
    val _rekening = MutableLiveData<UIState<ArrayList<RekeningModel>>>()
    private val _postTambahTestimoni = MutableLiveData<UIState<ArrayList<ResponseModel>>>()
    private val _postUpdateTestimoni = MutableLiveData<UIState<ArrayList<ResponseModel>>>()
    private val _postBuktiPembayaran = MutableLiveData<UIState<ArrayList<ResponseModel>>>()

    fun fetchTestimoni(
        idPemesanan: Int
    ){
        viewModelScope.launch {
            _fetchTestimoni.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = api.getTestimoniRiwayatPesanan(
                    "", idPemesanan
                )
                _fetchTestimoni.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _fetchTestimoni.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun fetchDetailRiwayatPesanan(idPemesanan: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _detailRiwayatPesanan.postValue(UIState.Loading)
            delay(1_000)
            try {
                val dataPembayaran = api.getRiwayatPesananDetail("", idPemesanan)
                _detailRiwayatPesanan.postValue(UIState.Success(dataPembayaran))
            } catch (ex: Exception) {
                _detailRiwayatPesanan.postValue(UIState.Failure("Error pada: ${ex.message}"))
            }
        }
    }

    fun fetchRekening(idWo: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _rekening.postValue(UIState.Loading)
            delay(1_000)
            try {
                val dataPembayaran = api.getWeddingOrganizerRekening("", idWo)
                _rekening.postValue(UIState.Success(dataPembayaran))
            } catch (ex: Exception) {
                _rekening.postValue(UIState.Failure("Error pada: ${ex.message}"))
            }
        }
    }



    fun postTambahData(
        idPemesanan: Int, idWo: Int, testimoni: String, bintang: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _postTambahTestimoni.postValue(UIState.Loading)
            delay(1_000)
            try {
                val postTambahTestimoni = api.postTambahTestimoni("", idPemesanan, idWo, testimoni, bintang)
                _postTambahTestimoni.postValue(UIState.Success(postTambahTestimoni))
            } catch (ex: Exception){
                _postTambahTestimoni.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun postUpdatehData(
        id_testimoni:String, testimoni:String, bintang:String,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _postUpdateTestimoni.postValue(UIState.Loading)
            delay(1_000)
            try {
                val postTambahTestimoni = api.postUpdateTestimoni("", id_testimoni, testimoni, bintang)
                _postUpdateTestimoni.postValue(UIState.Success(postTambahTestimoni))
            } catch (ex: Exception){
                _postUpdateTestimoni.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun postBuktiPembayaran(
        post: RequestBody, idPemesanan: RequestBody, gambar: MultipartBody.Part
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _postBuktiPembayaran.postValue(UIState.Loading)
            delay(1_000)
            try {
                val postTambahTestimoni = api.postBuktiPembayaran(post, idPemesanan, gambar)
                _postBuktiPembayaran.postValue(UIState.Success(postTambahTestimoni))
            } catch (ex: Exception){
                _postBuktiPembayaran.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun getTestimoni(): LiveData<UIState<ArrayList<TestimoniModel>>> = _fetchTestimoni
    fun getDetailRiwayatPesanan(): LiveData<UIState<ArrayList<RiwayatPesananModel>>> = _detailRiwayatPesanan
    fun getRekening(): LiveData<UIState<ArrayList<RekeningModel>>> = _rekening
    fun getResponseTambahTestimoni(): LiveData<UIState<ArrayList<ResponseModel>>> = _postTambahTestimoni
    fun getResponseUpdateTestimoni(): LiveData<UIState<ArrayList<ResponseModel>>> = _postUpdateTestimoni
    fun getResponseBuktiPembayaran(): LiveData<UIState<ArrayList<ResponseModel>>> = _postBuktiPembayaran

}