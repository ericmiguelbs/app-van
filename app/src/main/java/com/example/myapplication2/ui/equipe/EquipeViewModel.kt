package com.example.myapplication2.ui.equipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication2.data.DBHelper
import com.example.myapplication2.ui.equipe.Equipe

class EquipeViewModel(application: Application) : AndroidViewModel(application) {

    private val dbHelper = DBHelper(application)

    private val _equipes = MutableLiveData<List<Equipe>>()
    val equipes: LiveData<List<Equipe>> = _equipes

    init {
        carregarEquipesDoBanco()
    }

    fun carregarEquipesDoBanco() {
        val lista = dbHelper.getEquipes()
        _equipes.value = lista
    }
}