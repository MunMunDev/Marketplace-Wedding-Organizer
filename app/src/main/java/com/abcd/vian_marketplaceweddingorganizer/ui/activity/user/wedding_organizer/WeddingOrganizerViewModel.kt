package com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.wedding_organizer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.vian_marketplaceweddingorganizer.data.database.api.ApiService
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.WeddingOrganizerModel
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
    private var _weddingOrganizer = MutableLiveData<UIState<ArrayList<WeddingOrganizerModel>>>()

    fun fetchWeddingOrganizer(){
        viewModelScope.launch(Dispatchers.IO) {
            _weddingOrganizer.postValue(UIState.Loading)
            delay(1_000)
            try {
                val fetchWeddingOrganizer = api.getWeddingOrganizer("")
                _weddingOrganizer.postValue(UIState.Success(fetchWeddingOrganizer))
            } catch (ex: Exception){
                _weddingOrganizer.postValue(UIState.Failure("Gagal : ${ex.message}"))
            }
        }
    }

    fun getWeddingOrganizer(): LiveData<UIState<ArrayList<WeddingOrganizerModel>>> = _weddingOrganizer
}