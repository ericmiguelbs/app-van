package com.example.myapplication2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication2.R
import com.example.myapplication2.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Removemos a ViewModel e o text_home, que agora é text_home_title no XML estático

        // Configurar a navegação dos botões
        binding.btnNavigateEscolas.setOnClickListener {
            findNavController().navigate(R.id.nav_escola)
        }

        binding.btnNavigateAlunos.setOnClickListener {
            findNavController().navigate(R.id.nav_alunos)
        }

        binding.btnNavigateEquipes.setOnClickListener {
            findNavController().navigate(R.id.nav_equipe)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}