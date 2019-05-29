package com.jose.filmes.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.jose.filmes.db.FilmeDB
import com.jose.filmes.entity.Filme
import com.jose.filmes.repository.FilmeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FilmeViewModel(application: Application): AndroidViewModel(application) {

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
    get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: FilmeRepository
    val allFilmes: LiveData<List<Filme>>

    init {
        val filmeDao = FilmeDB.getDatabase(application).filmeDao()
        repository = FilmeRepository(filmeDao)
        allFilmes = repository.allFilmes
    }

    fun insert(filme: Filme) = scope.launch(Dispatchers.IO) {
        repository.insert(filme)
    }

    fun update(filme: Filme) = scope.launch(Dispatchers.IO) {
        repository.update(filme)
    }

    fun delete(filme: Filme) = scope.launch(Dispatchers.IO) {
        repository.delete(filme)
    }
}