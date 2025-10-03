package com.example.myapplication2.ui.equipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.R
import com.example.myapplication2.ui.equipe.Equipe

class EquipeAdapter(private var equipes: List<Equipe>) :
    RecyclerView.Adapter<EquipeAdapter.EquipeViewHolder>() {

    class EquipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idEquipe: TextView = view.findViewById(R.id.text_equipe_id)
        val nomeEquipe: TextView = view.findViewById(R.id.text_equipe_nome)
        val descricaoEquipe: TextView = view.findViewById(R.id.text_equipe_descricao)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_equipe, parent, false)
        return EquipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipeViewHolder, position: Int) {
        val equipe = equipes[position]

        holder.idEquipe.text = "${position + 1}."

        holder.nomeEquipe.text = equipe.nome

        holder.descricaoEquipe.text = "Descrição: ${equipe.descricao ?: "Não informada"}"
    }

    override fun getItemCount() = equipes.size

    fun updateEquipes(newEquipes: List<Equipe>) {
        equipes = newEquipes
        notifyDataSetChanged()
    }
}