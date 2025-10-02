package com.example.myapplication2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Esta é a tela inicial (Home)"
    }
    val text: LiveData<String> = _text
}
