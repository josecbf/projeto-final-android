package com.jose.filmes.ui

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.jose.filmes.R
import com.jose.filmes.entity.Filme
import kotlinx.android.synthetic.main.activity_filme.*
import java.lang.Exception

class FilmeActivity : AppCompatActivity() {

    lateinit var filme: Filme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filme)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        try {
            filme = intent.extras.get(EXTRA_REPLY) as Filme
            filme.let {
                etNome.setText(it.nome)
                etGenero.setText(it.genero)
                etAno.setText(it.ano.toString())
                etAtores.setText(it.atores)
            }
        } catch (e: Exception) {
            Log.d("TAG: ", e.message)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_projeto, menu)
        try {
            filme.let {
                val menuItem = menu?.findItem(R.id.menu_excluir)
                menuItem?.isVisible = true
            }
        } catch (e: Exception) {
            Log.d("TAG: ", "novo item " + e.message)
        }
        return true
    }

    // define as ações dos botões do menu
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if(item?.itemId == android.R.id.home){
            // fecha a atividade
            finish()
            true
        } else if (item?.itemId == R.id.menu_salve) {
            val retornaIntent = Intent()
            txtErro.text = ""

            if (etAno.text.toString().isEmpty() || etNome.text.toString().isEmpty() ||
                    etGenero.text.toString().isEmpty()) {
                txtErro.text = "Informe todos os dados"
                setResult(Activity.RESULT_CANCELED, retornaIntent)
            } else {
                try {
                    if ((::filme.isInitialized) && filme.id > 0) {
                        buildObject()
                    } else {
                        filme = Filme()
                        buildObject()
                    }
                    retornaIntent.putExtra(EXTRA_REPLY, filme)
                } catch (e: Exception) {
                    setResult(Activity.RESULT_CANCELED, retornaIntent)
                }
                setResult(Activity.RESULT_OK, retornaIntent)
                finish()
            }
            true
        } else if (item?.itemId == R.id.menu_excluir) {
            if ((::filme.isInitialized) && filme.id > 0) {
                val retornaIntent = Intent()
                retornaIntent.putExtra(EXTRA_DELETE, filme)
                setResult(Activity.RESULT_OK, retornaIntent)
                finish()
            }
            true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    private fun buildObject() {
        filme.nome = etNome.text.toString()
        filme.genero = etGenero.text.toString()
        filme.ano = etAno.text.toString().toInt()
        filme.atores = etAtores.text.toString()
    }

    companion object {
        const val EXTRA_REPLY = "com.jose.filmes.ui.REPLY"
        const val EXTRA_DELETE = "com.jose.filmes.ui.DELETE"
    }
}
