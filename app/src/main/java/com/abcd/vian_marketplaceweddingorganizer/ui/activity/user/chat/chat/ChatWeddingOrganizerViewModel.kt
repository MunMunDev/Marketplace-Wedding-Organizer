package com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.chat.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.vian_marketplaceweddingorganizer.data.database.api.ApiService
import com.abcd.vian_marketplaceweddingorganizer.data.model.MessageModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.ResponseModel
import com.abcd.vian_marketplaceweddingorganizer.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ChatWeddingOrganizerViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {
    private val _messageWeddingOrganizer = MutableLiveData<UIState<ArrayList<MessageModel>>>()
    private val _responseMessage = MutableLiveData<UIState<ArrayList<ResponseModel>>>()
    private val _postMessageImage = MutableLiveData<UIState<ArrayList<ResponseModel>>>()
    private val _postDeleteMessage = MutableLiveData<UIState<ArrayList<ResponseModel>>>()

    fun fetchChatWeddingOrganizer(idUser:Int, idReceived:Int){
        viewModelScope.launch(Dispatchers.IO) {
            _messageWeddingOrganizer.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = api.getChatWeddingOrganizer("", idUser, idReceived)
                _messageWeddingOrganizer.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _messageWeddingOrganizer.postValue(UIState.Failure("Error pada: ${ex.message}"))
            }
        }
    }

    fun postMessage(
        message: String, id_user: Int, id_wo:Int, pengirim: String
    ){
        viewModelScope.launch(Dispatchers.IO){
            _responseMessage.postValue(UIState.Loading)
            delay(1_000)
            try {
                val dataMessage = api.postMessage(
                    "", message, id_user, id_wo, pengirim
                )
                _responseMessage.postValue(UIState.Success(dataMessage))
            } catch (ex: Exception){
                _responseMessage.postValue(UIState.Failure("Error pada : ${ex.message}"))
            }
        }
    }

    fun postMessageImage(
        response: RequestBody, image: MultipartBody.Part, namaImage: RequestBody, id_user: RequestBody, id_wo:RequestBody, pengirim: RequestBody
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _postMessageImage.postValue(UIState.Loading)
            delay(1_000)
            try {
                val fetchMessageImagean = api.postMessageImage(
                    response, image, namaImage, id_user, id_wo, pengirim
                )
                _postMessageImage.postValue(UIState.Success(fetchMessageImagean))
            } catch (ex: Exception){
                _postMessageImage.postValue(UIState.Failure("Gagal : ${ex.message}"))
            }
        }
    }

    fun postDeleteMessage(
        id_message: Int
    ){
        viewModelScope.launch(Dispatchers.IO){
            _postDeleteMessage.postValue(UIState.Loading)
            delay(1_000)
            try {
                val dataMessage = api.postDeleteMessage(
                    "", id_message
                )
                _postDeleteMessage.postValue(UIState.Success(dataMessage))
            } catch (ex: Exception){
                _postDeleteMessage.postValue(UIState.Failure("Error pada : ${ex.message}"))
            }
        }
    }

    fun getChatWeddingOrganizer(): LiveData<UIState<ArrayList<MessageModel>>> = _messageWeddingOrganizer
    fun getMessage(): LiveData<UIState<ArrayList<ResponseModel>>> = _responseMessage
    fun getPostMessageImage(): LiveData<UIState<ArrayList<ResponseModel>>> = _postMessageImage
    fun getPostDeleteMessage(): LiveData<UIState<ArrayList<ResponseModel>>> = _postDeleteMessage
}