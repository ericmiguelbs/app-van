package com.example.myapplication2.ui.equipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.R

class EquipeAdapter(private var equipes: List<Equipe>) :
    RecyclerView.Adapter<EquipeAdapter.EquipeViewHolder>() {

    class EquipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val numero: TextView = itemView.findViewById(R.id.text_equipe_id)

        // Os demais IDs já estão corretos
        val nome: TextView = itemView.findViewById(R.id.text_equipe_nome)
        val descricao: TextView = itemView.findViewById(R.id.text_equipe_descricao)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_equipe, parent, false)
        return EquipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipeViewHolder, position: Int) {
        val equipe = equipes[position]

        holder.numero.text = "${position + 1}."

        holder.nome.text = equipe.nome
        holder.descricao.text = "Descrição: ${equipe.descricao ?: "Sem descrição"}"
    }

    override fun getItemCount(): Int = equipes.size

    fun updateEquipes(novasEquipes: List<Equipe>) {
        this.equipes = novasEquipes
        notifyDataSetChanged()
    }
}