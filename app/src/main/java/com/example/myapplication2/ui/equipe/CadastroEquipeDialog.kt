package com.example.myapplication2.ui.equipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myapplication2.R
import com.example.myapplication2.data.DBHelper
import com.example.myapplication2.ui.equipe.Equipe

class CadastroEquipeDialog(private val onEquipeSaved: () -> Unit) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_cadastro_equipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editNome = view.findViewById<EditText>(R.id.edit_nome_equipe)
        val editDescricao = view.findViewById<EditText>(R.id.edit_descricao_equipe)
        val btnSalvar = view.findViewById<Button>(R.id.btn_salvar_equipe)
        val dbHelper = DBHelper(requireContext())

        btnSalvar.setOnClickListener {
            val nome = editNome.text.toString().trim()
            val descricao = editDescricao.text.toString().trim()

            if (nome.isEmpty()) {
                Toast.makeText(requireContext(), "O nome é obrigatório!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val novaEquipe = Equipe(nome = nome, descricao = descricao.ifEmpty { null })
            val id = dbHelper.insertEquipe(novaEquipe)

            if (id > 0) {
                Toast.makeText(requireContext(), "Equipe cadastrada!", Toast.LENGTH_SHORT).show()
                onEquipeSaved.invoke()
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Erro ao cadastrar.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}