package com.abcd.vian_marketplaceweddingorganizer.ui.activity.wo.pesanan.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.vian_marketplaceweddingorganizer.data.database.api.ApiService
import com.abcd.vian_marketplaceweddingorganizer.data.model.RiwayatPesananListModel
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeddingOrganizerPesananViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {
    val _pesanan = MutableLiveData<UIState<ArrayList<RiwayatPesananListModel>>>()

    fun fetchPesanan(idWo: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _pesanan.postValue(UIState.Loading)
            delay(1_000)
            try {
                val dataPembayaran = api.getWeddingOrganizerPesananList("", idWo)
                _pesanan.postValue(UIState.Success(dataPembayaran))
            } catch (ex: Exception) {
                _pesanan.postValue(UIState.Failure("Error pada: ${ex.message}"))
            }
        }
    }

    fun getPesanan(): LiveData<UIState<ArrayList<RiwayatPesananListModel>>> = _pesanan
}