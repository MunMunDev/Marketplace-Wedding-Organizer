package com.abcd.vian_marketplaceweddingorganizer.ui.activity.admin.testimoni

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.vian_marketplaceweddingorganizer.data.database.api.ApiService
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.TestimoniModel
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminTestimoniViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {
    private var _testimoni = MutableLiveData<UIState<ArrayList<TestimoniModel>>>()
    private var _responsePostUpdateTestimoni = MutableLiveData<UIState<ArrayList<ResponseModel>>>()
    private var _responsePostDeleteTestimoni = MutableLiveData<UIState<ArrayList<ResponseModel>>>()

    fun fetchTestimoni() {
        viewModelScope.launch(Dispatchers.IO) {
            _testimoni.postValue(UIState.Loading)
            delay(1_000)
            try {
                val fetchTestimoni= api.getAllTestimoni("")
                _testimoni.postValue(UIState.Success(fetchTestimoni))
            } catch (ex: Exception) {
                _testimoni.postValue(UIState.Failure("Gagal : ${ex.message}"))
            }
        }
    }

    fun postUpdateTestimoni(
        idTestimoni: String, testimoni: String, bintang: String
    ){
        viewModelScope.launch(Dispatchers.IO){
            _responsePostUpdateTestimoni.postValue(UIState.Loading)
            try {
                val data = api.postUpdateTestimoni("", idTestimoni, testimoni, bintang)
                _responsePostUpdateTestimoni.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _responsePostUpdateTestimoni.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun postDeleteTestimoni(
        idTestimoni: String
    ){
        viewModelScope.launch(Dispatchers.IO){
            _responsePostDeleteTestimoni.postValue(UIState.Loading)
            try {
                val data = api.postDeleteTestimoni("", idTestimoni)
                _responsePostDeleteTestimoni.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _responsePostDeleteTestimoni.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun getTestimoni(): LiveData<UIState<ArrayList<TestimoniModel>>> = _testimoni
    fun getUpdateTestimoni(): LiveData<UIState<ArrayList<ResponseModel>>> = _responsePostUpdateTestimoni
    fun getDeleteTestimoni(): LiveData<UIState<ArrayList<ResponseModel>>> = _responsePostDeleteTestimoni
}