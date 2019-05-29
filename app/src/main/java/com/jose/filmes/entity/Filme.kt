package com.jose.filmes.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "filmes")
class Filme(): Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @NotNull
    @ColumnInfo(name = "ano")
    var ano: Int = 0

    @NotNull
    @ColumnInfo(name = "nome")
    var nome: String = ""

    @NotNull
    @ColumnInfo(name = "genero")
    var genero: String = ""

    @NotNull
    @ColumnInfo(name = "atores")
    var atores: String = ""

}