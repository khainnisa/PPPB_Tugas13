package com.example.tugaspertemuan13

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Pemilih::class], version = 1)
abstract class PemilihDatabase : RoomDatabase() {
    abstract fun PemilihDao(): PemilihDao
}
