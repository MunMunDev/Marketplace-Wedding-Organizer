package com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.riwayat_pesanan.print

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.vian_marketplaceweddingorganizer.data.database.api.ApiService
import com.abcd.vian_marketplaceweddingorganizer.data.model.RiwayatPesananModel
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeddingOrganizerViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {
    private var _pesanan = MutableLiveData<UIState<ArrayList<RiwayatPesananModel>>>()

    fun fetchPesanan(ket: String){
        viewModelScope.launch(Dispatchers.IO) {
            _pesanan.postValue(UIState.Loading)
            delay(1_000)
            try {
                val fetchPesanan = api.getAdminPrintLaporan("", ket)
                _pesanan.postValue(UIState.Success(fetchPesanan))
            } catch (ex: Exception){
                _pesanan.postValue(UIState.Failure("Gagal : ${ex.message}"))
            }
        }
    }
    fun getPesanan(): LiveData<UIState<ArrayList<RiwayatPesananModel>>> = _pesanan
}