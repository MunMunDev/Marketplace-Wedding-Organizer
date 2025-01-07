package com.abcd.vian_marketplaceweddingorganizer.ui.activity.chat.list_chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.vian_marketplaceweddingorganizer.data.database.api.ApiService
import com.abcd.vian_marketplaceweddingorganizer.data.model.AlamatModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.MessageModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListWeddingOrganizerViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {
    val _lisMessage = MutableLiveData<UIState<ArrayList<MessageModel>>>()
    fun fetchChatListWeddingOrganizer(idUser:Int){
        viewModelScope.launch(Dispatchers.IO) {
            _lisMessage.postValue(UIState.Loading)
            delay(1_000)
            try {
                val dataPembayaran = api.getChatListWeddingOrganizer("", idUser)
                _lisMessage.postValue(UIState.Success(dataPembayaran))
            } catch (ex: Exception){
                _lisMessage.postValue(UIState.Failure("Error pada: ${ex.message}"))
            }
        }
    }

    fun getChatListWeddingOrganizer(): LiveData<UIState<ArrayList<MessageModel>>> = _lisMessage
}