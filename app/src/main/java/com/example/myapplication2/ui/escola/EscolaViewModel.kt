package com.example.myapplication2.ui.escola

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication2.data.DBHelper
class EscolaViewModel(application: Application) : AndroidViewModel(application) {

    private val dbHelper = DBHelper(application)

    private val _escolas = MutableLiveData<List<Escola>>()
    val escolas: LiveData<List<Escola>> = _escolas

    // Método para carregar as escolas do banco de dados (chamado pelo Fragmento)
    fun carregarEscolasDoBanco() {
        val lista = dbHelper.getEscolas()
        _escolas.value = lista
    }

    private val _text = MutableLiveData<String>().apply {
        value = "Lista de Escolas"
    }
    val text: LiveData<String> = _text
}