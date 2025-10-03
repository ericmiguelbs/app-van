package com.example.myapplication2.ui.escola

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.R

class EscolaAdapter(
    private var escolas: List<Escola>,
    private val onClick: (Escola) -> Unit
) : RecyclerView.Adapter<EscolaAdapter.EscolaViewHolder>() {

    class EscolaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idEscola: TextView = view.findViewById(R.id.text_escola_id)
        val nomeEscola: TextView = view.findViewById(R.id.text_escola_nome)
        val enderecoEscola: TextView = view.findViewById(R.id.text_escola_endereco)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EscolaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_escola, parent, false)
        return EscolaViewHolder(view)
    }

    override fun onBindViewHolder(holder: EscolaViewHolder, position: Int) {
        val escola = escolas[position]

        holder.idEscola.text = "${position + 1}."
        holder.nomeEscola.text = escola.nome
        holder.enderecoEscola.text = "Endereço: ${escola.endereco ?: "Não informado"}"

        holder.itemView.setOnClickListener {
            onClick(escola)
        }
    }

    override fun getItemCount() = escolas.size

    fun updateEscolas(newEscolas: List<Escola>) {
        escolas = newEscolas
        notifyDataSetChanged()
    }
}