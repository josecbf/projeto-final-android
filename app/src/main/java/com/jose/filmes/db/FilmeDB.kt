package com.jose.filmes.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.jose.filmes.entity.Filme

@Database(entities = [Filme::class], version = 1)
abstract class FilmeDB: RoomDatabase() {

    abstract fun filmeDao(): FilmeDAO

    companion object {
        private var INSTANCE: FilmeDB? = null

        fun getDatabase(context: Context): FilmeDB {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FilmeDB::class.java,
                    "filmes_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}