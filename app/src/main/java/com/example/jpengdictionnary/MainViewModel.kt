package com.example.jpengdictionnary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _wordData = MutableLiveData<List<WordData>>()
    val wordData: LiveData<List<WordData>> get() = _wordData

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun searchWords(keyword: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiClient.jishoApiService.searchWords(keyword)
                _wordData.value = response.data
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}