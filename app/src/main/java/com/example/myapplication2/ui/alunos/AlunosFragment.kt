package com.example.myapplication2.ui.alunos

import DBHelper
import AlunosAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication2.databinding.FragmentAlunosBinding

class AlunosFragment : Fragment() {

    private var _binding: FragmentAlunosBinding? = null
    private val binding get() = _binding!!

    private val alunosViewModel: AlunosViewModel by viewModels()

    private lateinit var dbHelper: DBHelper
    private lateinit var alunosAdapter: AlunosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlunosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dbHelper = DBHelper(requireContext())

        alunosAdapter = AlunosAdapter(emptyList())
        binding.recyclerAlunos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerAlunos.adapter = alunosAdapter

        binding.btnSalvar.setOnClickListener {
            val nome = binding.inputNome.text.toString().trim()
            val idadeStr = binding.inputIdade.text.toString().trim()

            if (nome.isNotEmpty() && idadeStr.isNotEmpty()) {
                try {
                    val idadeInt = idadeStr.toInt()

                    dbHelper.adicionarAluno(nome, idadeInt)

                    Toast.makeText(requireContext(), "Aluno $nome cadastrado! ✅", Toast.LENGTH_SHORT).show()

                    binding.inputNome.setText("")
                    binding.inputIdade.setText("")

                    if (binding.recyclerAlunos.visibility == View.VISIBLE) {
                        carregarAlunos()
                    }

                } catch (e: NumberFormatException) {
                    Toast.makeText(requireContext(), "Erro: idade inválida.", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnMostrarAlunos.setOnClickListener {
            alternarVisualizacaoAlunos()
        }

        return root
    }

    private fun alternarVisualizacaoAlunos() {
        if (binding.recyclerAlunos.visibility == View.GONE) {
            carregarAlunos()
            binding.recyclerAlunos.visibility = View.VISIBLE
            binding.btnMostrarAlunos.text = "Ocultar Alunos"
            Toast.makeText(requireContext(), "Lista de alunos exibida.", Toast.LENGTH_SHORT).show()
        } else {
            binding.recyclerAlunos.visibility = View.GONE
            binding.btnMostrarAlunos.text = "Mostrar Alunos"
        }
    }
    private fun carregarAlunos() {
        val listaAlunos = dbHelper.listarAlunos()
        alunosAdapter.setAlunos(listaAlunos)

        if (listaAlunos.isEmpty()) {
            Toast.makeText(requireContext(), "Nenhum aluno cadastrado.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}