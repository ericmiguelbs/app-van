package com.example.myapplication2.ui.escola

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.R
import com.example.myapplication2.data.DBHelper
import com.example.myapplication2.ui.alunos.Aluno
import com.example.myapplication2.ui.alunos.AlunoAdapter
import com.example.myapplication2.ui.equipe.Equipe
import com.example.myapplication2.ui.equipe.EquipeAdapter

class EscolaFragment : Fragment() {

    private lateinit var escolaViewModel: EscolaViewModel
    private lateinit var dbHelper: DBHelper

    private lateinit var editNome: EditText
    private lateinit var editEndereco: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnMostrarEscolas: Button

    private lateinit var recyclerListaEscolas: RecyclerView
    private lateinit var escolaAdapter: EscolaAdapter

    private lateinit var recyclerAlunos: RecyclerView
    private lateinit var recyclerEquipes: RecyclerView
    private lateinit var textTituloAlunos: TextView
    private lateinit var textTituloEquipes: TextView
    private lateinit var alunoAdapter: AlunoAdapter
    private lateinit var equipeAdapter: EquipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_escola, container, false)

        // Inicializar
        escolaViewModel = ViewModelProvider(this).get(EscolaViewModel::class.java)
        dbHelper = DBHelper(requireContext())

        editNome = root.findViewById(R.id.edit_nome_escola)
        editEndereco = root.findViewById(R.id.edit_endereco_escola)
        btnSalvar = root.findViewById(R.id.btn_salvar_escola)
        btnMostrarEscolas = root.findViewById(R.id.btn_mostrar_escolas)
        recyclerListaEscolas = root.findViewById(R.id.recycler_lista_escolas)

        recyclerAlunos = root.findViewById(R.id.recycler_alunos_por_escola)
        recyclerEquipes = root.findViewById(R.id.recycler_equipes_por_escola)
        textTituloAlunos = root.findViewById(R.id.text_titulo_alunos)
        textTituloEquipes = root.findViewById(R.id.text_titulo_equipes)

        configurarListas()

        btnSalvar.setOnClickListener { cadastrarEscola() }
        btnMostrarEscolas.setOnClickListener { alternarVisualizacaoEscolas() }

        ocultarDetalhes()
        carregarEscolas()

        return root
    }

    private fun configurarListas() {
        escolaAdapter = EscolaAdapter(emptyList()) { escolaSelecionada ->
            carregarDetalhesEscola(escolaSelecionada)
        }
        recyclerListaEscolas.adapter = escolaAdapter
        recyclerListaEscolas.layoutManager = LinearLayoutManager(context)

        alunoAdapter = AlunoAdapter(emptyList())
        recyclerAlunos.adapter = alunoAdapter
        recyclerAlunos.layoutManager = LinearLayoutManager(context)

        equipeAdapter = EquipeAdapter(emptyList())
        recyclerEquipes.adapter = equipeAdapter
        recyclerEquipes.layoutManager = LinearLayoutManager(context)
    }
    private fun carregarDetalhesEscola(escola: Escola) {
        val escolaId = escola.id

        val alunos = dbHelper.listarAlunos(escolaId)
        alunoAdapter.updateAlunos(alunos)

        val equipes = dbHelper.getEquipes(escolaId)
        equipeAdapter.updateEquipes(equipes)

        textTituloAlunos.text = "Alunos de ${escola.nome}"
        textTituloEquipes.text = "Equipes de ${escola.nome}"

        textTituloAlunos.visibility = View.VISIBLE
        recyclerAlunos.visibility = View.VISIBLE

        textTituloEquipes.visibility = View.VISIBLE
        recyclerEquipes.visibility = View.VISIBLE
    }
    private fun ocultarDetalhes() {
        textTituloAlunos.visibility = View.GONE
        recyclerAlunos.visibility = View.GONE

        textTituloEquipes.visibility = View.GONE
        recyclerEquipes.visibility = View.GONE
    }

    private fun carregarEscolas() {
        val listaEscolas = dbHelper.getEscolas()
        escolaAdapter.updateEscolas(listaEscolas)

        if (listaEscolas.isEmpty() && recyclerListaEscolas.visibility == View.VISIBLE) {
            Toast.makeText(requireContext(), "Nenhuma escola cadastrada.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cadastrarEscola() {
        val nome = editNome.text.toString().trim()
        val endereco = editEndereco.text.toString().trim()

        if (nome.isEmpty()) {
            Toast.makeText(context, "O nome da escola é obrigatório!", Toast.LENGTH_SHORT).show()
            return
        }

        val novaEscola = Escola(nome = nome, endereco = endereco.ifEmpty { null })
        val id = dbHelper.insertEscola(novaEscola)

        if (id > 0) {
            Toast.makeText(context, "Escola '$nome' cadastrada! ✅", Toast.LENGTH_SHORT).show()
            editNome.text.clear()
            editEndereco.text.clear()
            carregarEscolas()
        } else {
            Toast.makeText(context, "Erro ao cadastrar escola.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun alternarVisualizacaoEscolas() {
        if (recyclerListaEscolas.visibility == View.GONE) {
            carregarEscolas()
            recyclerListaEscolas.visibility = View.VISIBLE
            btnMostrarEscolas.text = "Ocultar Escolas"
            Toast.makeText(requireContext(), "Lista de escolas exibida.", Toast.LENGTH_SHORT).show()
        } else {
            recyclerListaEscolas.visibility = View.GONE
            btnMostrarEscolas.text = "Mostrar Escolas"
            ocultarDetalhes()
        }
    }
}