package com.example.myapplication2.ui.equipe

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.R
import com.example.myapplication2.ui.equipe.Equipe
import com.example.myapplication2.data.DBHelper

class EquipeFragment : Fragment() {
    private lateinit var equipeViewModel: EquipeViewModel
    private lateinit var equipeAdapter: EquipeAdapter
    private lateinit var dbHelper: DBHelper
    private lateinit var editNome: EditText
    private lateinit var editDescricao: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnMostrarEquipes: Button
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_equipe, container, false)

        equipeViewModel = ViewModelProvider(this).get(EquipeViewModel::class.java)
        dbHelper = DBHelper(requireContext())

        editNome = root.findViewById(R.id.edit_nome_equipe)
        editDescricao = root.findViewById(R.id.edit_descricao_equipe)
        btnSalvar = root.findViewById(R.id.btn_salvar_equipe)
        recyclerView = root.findViewById(R.id.recycler_view_equipes)
        btnMostrarEquipes = root.findViewById(R.id.btn_mostrar_equipes)

        equipeAdapter = EquipeAdapter(emptyList())
        recyclerView.adapter = equipeAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        btnSalvar.setOnClickListener {
            cadastrarEquipe()
        }

        btnMostrarEquipes.setOnClickListener {
            alternarVisualizacaoEquipes()
        }

        return root
    }

    private fun cadastrarEquipe() {
        val nome = editNome.text.toString().trim()
        val descricao = editDescricao.text.toString().trim()

        if (nome.isEmpty()) {
            Toast.makeText(context, "O nome da equipe é obrigatório!", Toast.LENGTH_SHORT).show()
            return
        }

        val novaEquipe = Equipe(nome = nome, descricao = descricao.ifEmpty { null })

        val id = dbHelper.insertEquipe(novaEquipe)

        if (id > 0) {
            Toast.makeText(context, "Equipe '$nome' cadastrada! ✅", Toast.LENGTH_SHORT).show()

            editNome.text.clear()
            editDescricao.text.clear()

            if (recyclerView.visibility == View.VISIBLE) {
                carregarEquipes()
            }
        } else {
            Toast.makeText(context, "Erro ao cadastrar equipe.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun carregarEquipes() {
        val listaEquipes = dbHelper.getEquipes()
        equipeAdapter.updateEquipes(listaEquipes)

        if (listaEquipes.isEmpty() && recyclerView.visibility == View.VISIBLE) {
            Toast.makeText(requireContext(), "Nenhuma equipe cadastrada.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun alternarVisualizacaoEquipes() {
        if (recyclerView.visibility == View.GONE) {
            carregarEquipes()
            recyclerView.visibility = View.VISIBLE
            btnMostrarEquipes.text = "Ocultar Equipes"
            Toast.makeText(requireContext(), "Lista de equipes exibida.", Toast.LENGTH_SHORT).show()
        } else {
            recyclerView.visibility = View.GONE
            btnMostrarEquipes.text = "Mostrar Equipes"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}