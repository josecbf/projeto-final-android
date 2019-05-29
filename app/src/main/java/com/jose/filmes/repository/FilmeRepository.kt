package com.jose.filmes.repository

import android.arch.lifecycle.LiveData
import com.jose.filmes.db.FilmeDAO
import com.jose.filmes.entity.Filme

class FilmeRepository(private val filmeDao: FilmeDAO) {

    val allFilmes: LiveData<List<Filme>> = filmeDao.getAll()

    fun insert(filme: Filme) {
        filmeDao.insert(filme)
    }

    fun update(filme: Filme) {
        filmeDao.update(filme)
    }

    fun delete(filme: Filme) {
        filmeDao.delete(filme)
    }
}