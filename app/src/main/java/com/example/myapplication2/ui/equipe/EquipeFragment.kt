package com.example.myapplication2.ui.equipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.R
import com.example.myapplication2.data.DBHelper
import com.example.myapplication2.ui.escola.Escola

class EquipeFragment : Fragment() {

    private lateinit var dbHelper: DBHelper

    private lateinit var editNome: EditText
    private lateinit var editDescricao: EditText
    private lateinit var btnCadastrarEquipe: Button

    private lateinit var spinnerEscola: Spinner
    private var listaEscolas: List<Escola> = emptyList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var equipeAdapter: EquipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_equipe, container, false)
        dbHelper = DBHelper(requireContext())

        editNome = root.findViewById(R.id.edit_nome_equipe)
        editDescricao = root.findViewById(R.id.edit_descricao_equipe)
        btnCadastrarEquipe = root.findViewById(R.id.btn_cadastrar_equipe)
        spinnerEscola = root.findViewById(R.id.spinner_escola_equipe) // NOVO

        recyclerView = root.findViewById(R.id.recycler_equipes)
        recyclerView.layoutManager = LinearLayoutManager(context)
        equipeAdapter = EquipeAdapter(emptyList())
        recyclerView.adapter = equipeAdapter

        carregarEscolasParaSpinner()

        btnCadastrarEquipe.setOnClickListener {
            cadastrarEquipe()
        }

        carregarListaEquipes()
        return root
    }
    private fun carregarEscolasParaSpinner() {

        listaEscolas = dbHelper.getEscolas()

        val nomesEscolas = listaEscolas.map { it.nome }

        val displayList = if (nomesEscolas.isEmpty())
            listOf("Nenhuma escola cadastrada")
        else
            nomesEscolas

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            displayList
        )
        spinnerEscola.adapter = adapter
    }

    private fun cadastrarEquipe() {
        val nome = editNome.text.toString().trim()
        val descricao = editDescricao.text.toString().trim()

        if (nome.isEmpty()) {
            Toast.makeText(context, "O nome da equipe é obrigatório!", Toast.LENGTH_SHORT).show()
            return
        }

        val pos = spinnerEscola.selectedItemPosition

        if (listaEscolas.isEmpty() || pos < 0 || spinnerEscola.selectedItem == "Nenhuma escola cadastrada") {
            Toast.makeText(context, "É obrigatório selecionar uma Escola!", Toast.LENGTH_LONG).show()
            return
        }

        val escolaIdSelecionada: Int? = listaEscolas[pos].id

        val novaEquipe = Equipe(
            nome = nome,
            descricao = descricao.ifEmpty { null },
            escolaId = escolaIdSelecionada
        )

        val id = dbHelper.insertEquipe(novaEquipe)

        if (id > 0) {

            Toast.makeText(context, "Equipe '$nome' cadastrada! ✅", Toast.LENGTH_SHORT).show()

            editNome.text.clear()
            editDescricao.text.clear()
            carregarListaEquipes()
        } else {
            Toast.makeText(context, "Erro ao cadastrar equipe.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun carregarListaEquipes() {
        val lista = dbHelper.getEquipes()
        equipeAdapter.updateEquipes(lista)
    }

}