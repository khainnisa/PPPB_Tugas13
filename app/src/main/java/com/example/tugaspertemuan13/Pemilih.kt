package com.example.tugaspertemuan13

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pemilih")
data class Pemilih(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val nik: String,
    val gender: String,
    val address: String
)

