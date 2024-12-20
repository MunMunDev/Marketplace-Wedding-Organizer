package com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.vian_marketplaceweddingorganizer.data.database.api.ApiService
import com.abcd.vian_marketplaceweddingorganizer.data.model.AlamatModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {
    val _pembayaran = MutableLiveData<UIState<ArrayList<AlamatModel>>>()
    val _responseRegistrasiPembayaran = MutableLiveData<UIState<ArrayList<ResponseModel>>>()
    private val _postPesan = MutableLiveData<UIState<ArrayList<ResponseModel>>>()

    fun fetchAlamat(idUser:Int){
        viewModelScope.launch(Dispatchers.IO) {
            _pembayaran.postValue(UIState.Loading)
            delay(1_000)
            try {
                val dataPembayaran = api.getAlamat("", idUser)
                _pembayaran.postValue(UIState.Success(dataPembayaran))
            } catch (ex: Exception){
                _pembayaran.postValue(UIState.Failure("Error pada: ${ex.message}"))
            }
        }
    }


    fun postRegistrasiPembayaran(
        id_pembayaran: String, idUser: String, keterangan:String, namaLengkap: String,
        nomorHp: String, kecamatanKabKota:String, alamat:String, detailAlamat:String
    ){
        viewModelScope.launch(Dispatchers.IO){
            _responseRegistrasiPembayaran.postValue(UIState.Loading)
            delay(1_000)
            try {
                val dataRegistrasiPembayaran = api.postRegistrasiPembayaran(
                    "", id_pembayaran, idUser, keterangan, namaLengkap, nomorHp, kecamatanKabKota, alamat, detailAlamat
                )
                _responseRegistrasiPembayaran.postValue(UIState.Success(dataRegistrasiPembayaran))
            } catch (ex: Exception){
                _responseRegistrasiPembayaran.postValue(UIState.Failure("Error pada : ${ex.message}"))
            }
        }
    }

    fun postPesan(idUser:String, namaLengkap:String, nomorHp:String, alamat:String, detailAlamat:String, metode_pembayaran: String){
        viewModelScope.launch(Dispatchers.IO) {
            _postPesan.postValue(UIState.Loading)
            delay(1_000)
            try {
                val fetchPesanan = api.postPesan("", idUser, namaLengkap, nomorHp, alamat, detailAlamat,metode_pembayaran)
                _postPesan.postValue(UIState.Success(fetchPesanan))
            } catch (ex: Exception){
                _postPesan.postValue(UIState.Failure("Gagal : ${ex.message}"))
            }
        }
    }

    fun getAlamat(): LiveData<UIState<ArrayList<AlamatModel>>> = _pembayaran
    fun getRegistrasiPembayaran(): LiveData<UIState<ArrayList<ResponseModel>>> = _responseRegistrasiPembayaran
    fun getPostPesan(): LiveData<UIState<ArrayList<ResponseModel>>> = _postPesan
}