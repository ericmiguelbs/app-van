package com.example.myapplication2.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Aqui é a tela Gallery"
    }
    val text: LiveData<String> = _text
}
