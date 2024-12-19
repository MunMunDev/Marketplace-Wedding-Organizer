package com.abcd.vian_marketplaceweddingorganizer.ui.activity.user.main

import androidx.lifecycle.ViewModel
import com.abcd.vian_marketplaceweddingorganizer.data.database.api.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {

}