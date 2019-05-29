package com.jose.filmes.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.jose.filmes.entity.Filme

@Dao
interface FilmeDAO {

    @Insert
    fun insert(filme: Filme)

    @Update
    fun update(filme: Filme)

    @Delete
    fun delete(filme: Filme)

    @Query(value = "SELECT * FROM filmes ORDER BY nome ASC")
    fun getAll(): LiveData<List<Filme>>

    @Query(value = "SELECT * FROM filmes WHERE id = :id")
    fun getFilme(id: Int): LiveData<Filme>
}