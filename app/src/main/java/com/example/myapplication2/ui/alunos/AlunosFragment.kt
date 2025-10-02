package com.example.myapplication2.ui.alunos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication2.databinding.FragmentAlunosBinding

class AlunosFragment : Fragment() {

    private var _binding: FragmentAlunosBinding? = null
    private val binding get() = _binding!!

    private val alunosViewModel: AlunosViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlunosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        alunosViewModel.text.observe(viewLifecycleOwner) {
            binding.textAlunos.text = it
        }

        binding.btnSalvar.setOnClickListener {
            val nome = binding.inputNome.text.toString()
            val idade = binding.inputIdade.text.toString()

            if (nome.isNotEmpty() && idade.isNotEmpty()) {
                binding.textAlunos.text =
                    "Aluno $nome, $idade anos cadastrado com sucesso!"
            } else {
                binding.textAlunos.text = "Preencha todos os campos!"
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
