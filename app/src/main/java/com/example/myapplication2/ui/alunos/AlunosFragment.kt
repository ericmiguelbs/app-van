package com.example.myapplication2.ui.alunos

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

class AlunosFragment : Fragment() {

    private lateinit var dbHelper: DBHelper

    private lateinit var inputNome: EditText
    private lateinit var inputIdade: EditText
    private lateinit var btnSalvarAluno: Button

    private lateinit var spinnerEscola: Spinner
    private var listaEscolas: List<Escola> = emptyList()

    private lateinit var recyclerView: RecyclerView
    private lateinit var alunoAdapter: AlunoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_alunos, container, false)
        dbHelper = DBHelper(requireContext())

        // Mapeamento das views
        inputNome = root.findViewById(R.id.input_nome)
        inputIdade = root.findViewById(R.id.input_idade)
        btnSalvarAluno = root.findViewById(R.id.btn_salvar_aluno)
        spinnerEscola = root.findViewById(R.id.spinner_escola_aluno) // NOVO

        recyclerView = root.findViewById(R.id.recycler_alunos)
        recyclerView.layoutManager = LinearLayoutManager(context)
        alunoAdapter = AlunoAdapter(emptyList())
        recyclerView.adapter = alunoAdapter

        carregarEscolasParaSpinner()

        btnSalvarAluno.setOnClickListener {
            adicionarAluno()
        }

        carregarListaAlunos()
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

    // Função de cadastro de aluno (MODIFICADA)
    private fun adicionarAluno() {
        val nome = inputNome.text.toString().trim()
        val idadeStr = inputIdade.text.toString().trim()

        if (nome.isEmpty() || idadeStr.isEmpty()) {
            Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            return
        }

        val idadeInt = idadeStr.toIntOrNull()
        if (idadeInt == null || idadeInt <= 0) {
            Toast.makeText(context, "Idade inválida!", Toast.LENGTH_SHORT).show()
            return
        }

        val pos = spinnerEscola.selectedItemPosition

        val isPlaceholderSelected = spinnerEscola.selectedItem.toString() == "Nenhuma escola cadastrada"

        if (listaEscolas.isEmpty() || isPlaceholderSelected) {
            Toast.makeText(context, "É obrigatório selecionar uma Escola para cadastrar o aluno!", Toast.LENGTH_LONG).show()
            return
        }

        val escolaIdSelecionada: Int = listaEscolas[pos].id

        val novoAluno = Aluno(
            nome = nome,
            idade = idadeInt,
            escolaId = escolaIdSelecionada
        )

        val id = dbHelper.adicionarAluno(novoAluno)

        if (id > 0) {
            Toast.makeText(context, "Aluno '$nome' cadastrado! ✅", Toast.LENGTH_SHORT).show()
            inputNome.text.clear()
            inputIdade.text.clear()
            carregarListaAlunos()
        } else {
            Toast.makeText(context, "Erro ao cadastrar aluno.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun carregarListaAlunos() {
        val lista = dbHelper.listarAlunos()
        alunoAdapter.updateAlunos(lista)
    }

}