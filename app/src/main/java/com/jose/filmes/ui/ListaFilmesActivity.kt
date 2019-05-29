package com.jose.filmes.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.jose.filmes.R
import com.jose.filmes.adapter.FilmeRecyclerAdapter
import com.jose.filmes.entity.Filme
import com.jose.filmes.viewmodel.FilmeViewModel
import kotlinx.android.synthetic.main.activity_lista_filmes.*

class ListaFilmesActivity : AppCompatActivity() {

    private lateinit var filmeViewModel: FilmeViewModel
    lateinit var notificationManager: NotificationManagerCompat
    val CHANNEL_ID = "com.jose.filmes.ui.CHANNEL_ID"
    val REQUEST_CODE = 12
    val REQUEST_CODE_UPDATE = 13

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_filmes)

        val recyclerView_ = findViewById<RecyclerView>(R.id.recyclerView)
        montarLista(recyclerView_)
        notificationManager = NotificationManagerCompat.from(this)

        btnNovo.setOnClickListener() {
            val intent = Intent(this, FilmeActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    private fun montarLista(recyclerView_: RecyclerView) {
        recyclerView_.setHasFixedSize(true)

        // responsável por medir e posicionar as visualizações dos itens
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        recyclerView_.layoutManager = layoutManager
        val adapter = FilmeRecyclerAdapter()

        adapter.onItemClickListener = { it ->
            val intent = Intent(this@ListaFilmesActivity, FilmeActivity::class.java)
            intent.putExtra(FilmeActivity.EXTRA_REPLY, it)
            startActivityForResult(intent, REQUEST_CODE_UPDATE)
        }

        recyclerView_.adapter = adapter

        filmeViewModel = ViewModelProviders.of(this)
            .get(FilmeViewModel::class.java)
        filmeViewModel.allFilmes.observe(this, Observer {
            filme -> filme?.let {
                adapter.setFilmeList(it)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                    result ->
                try {
                    val filme = result.extras?.get(FilmeActivity.EXTRA_REPLY) as Filme
                    filme.let {
                        filmeViewModel.insert(filme)
                    }
                } catch (e: Exception) {
                    Log.d("TAG ", e.message)
                }
            }
        } else if (requestCode == REQUEST_CODE_UPDATE && resultCode == Activity.RESULT_OK) {
            data?.let { result ->
                try {
                    var filme: Filme? = result.extras?.get(FilmeActivity.EXTRA_REPLY) as? Filme

                    if (filme == null) {
                        filme = result.extras?.get(FilmeActivity.EXTRA_DELETE) as? Filme
                        filme.let {
                            showNotification("Excluído filme: ",  it!!.ano.toString() + " - " + it!!.nome)
                            filmeViewModel.delete(filme!!)
                        }
                    } else {
                        filme.let {
                            filmeViewModel.update(filme)
                        }
                    }
                } catch (e: Exception) {
                    Log.d("TAG ", e.message)
                }
            }
        }
    }

    private fun showNotification(t: String, c: String) {
        val notification =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentTitle(t)
                .setContentText(c)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
        notificationManager.notify(1, notification)
    }
}
