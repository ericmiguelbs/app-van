package com.example.myapplication2.ui.alunos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.R

class AlunoAdapter(private var alunos: List<Aluno>) :
    RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder>() {

    class AlunoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numero: TextView = itemView.findViewById(R.id.text_id)
        val nome: TextView = itemView.findViewById(R.id.text_nome)
        val idade: TextView = itemView.findViewById(R.id.text_idade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlunoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_aluno, parent, false)
        return AlunoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlunoViewHolder, position: Int) {
        val aluno = alunos[position]

        holder.numero.text = "${position + 1}."

        holder.nome.text = aluno.nome
        holder.idade.text = "Idade: ${aluno.idade} anos"
    }

    override fun getItemCount(): Int = alunos.size

    fun updateAlunos(novosAlunos: List<Aluno>) {
        this.alunos = novosAlunos
        notifyDataSetChanged()
    }
}