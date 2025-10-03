package com.example.myapplication2.ui.alunos
data class Aluno(
    val id: Int = 0,
    val nome: String,
    val idade: Int,
    val escolaId: Int? = null
)