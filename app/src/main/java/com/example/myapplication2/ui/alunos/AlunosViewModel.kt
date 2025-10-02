package com.example.myapplication2.ui.alunos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlunosViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Tela de Cadastro de Alunos"
    }
    val text: LiveData<String> = _text
}
