package com.jose.filmes.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jose.filmes.R
import com.jose.filmes.entity.Filme
import kotlinx.android.synthetic.main.item_lista.view.*

class FilmeRecyclerAdapter() : RecyclerView.Adapter<FilmeRecyclerAdapter.ViewHolder>() {

    lateinit var onItemClickListener: ((Filme) -> Unit)
    private var filmeList = emptyList<Filme>()

    // inflar o item da lista no recycler view
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista, parent, false)
        return ViewHolder(view)
    }

    //retorna o tamanho da lista
    override fun getItemCount(): Int = filmeList.count()

    // insere os elementos da lista no item da lista do recycler
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filme = filmeList[position]
        holder.txtAno.text = filme.ano.toString()
        holder.txtNome.text = filme.nome
        holder.txtGenero.text = filme.genero
        holder.txtAtores.text = filme.atores
    }

    // atualiza os dados da lista
    fun setFilmeList(filmes: List<Filme>) {
        this.filmeList = filmes
        notifyDataSetChanged()
    }

    // classe para mapear os componentes do item da lista
    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onItemClickListener.invoke(filmeList[adapterPosition])
            }
        }

        val txtAno: TextView = view.tvAno
        val txtNome: TextView = view.tvNome
        val txtGenero: TextView = view.tvGenero
        val txtAtores: TextView = view.tvAtores
    }
}