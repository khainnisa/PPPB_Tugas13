package com.example.tugaspertemuan13

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PemilihDao {
    @Insert
    suspend fun insertPemilih(pemilih: Pemilih)

    @Update
    suspend fun updatePemilih(pemilih: Pemilih)

    @Delete
    suspend fun deletePemilih(pemilih: Pemilih)

    @Query("SELECT * FROM pemilih")
    suspend fun getAllPemilih(): List<Pemilih>
}



