package com.example.tugaspertemuan13

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.tugaspertemuan13.LoginActivity
import com.example.tugaspertemuan13.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: PemilihDatabase
    private lateinit var adapter: PemilihAdapter
    private val pemilihList = mutableListOf<Pemilih>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(applicationContext, PemilihDatabase::class.java, "pemilih-db")
            .build()

        adapter = PemilihAdapter(pemilihList, onEditClick = { pemilih ->
            val intent = Intent(this, EditDataActivity::class.java)
            intent.putExtra("ID", pemilih.id)
            intent.putExtra("NAME", pemilih.name)
            intent.putExtra("NIK", pemilih.nik)
            intent.putExtra("GENDER", pemilih.gender)
            intent.putExtra("ADDRESS", pemilih.address)
            startActivityForResult(intent, EDIT_REQUEST_CODE)
        }, onDetailClick = { pemilih ->
            val intent = Intent(this, DetailDataActivity::class.java)
            intent.putExtra("NAME", pemilih.name)
            intent.putExtra("NIK", pemilih.nik)
            intent.putExtra("GENDER", pemilih.gender)
            intent.putExtra("ADDRESS", pemilih.address)
            startActivity(intent)
        })

        binding.pemilihRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.pemilihRecyclerView.adapter = adapter

        binding.addDataButton.setOnClickListener {
            val intent = Intent(this, AddDataActivity::class.java)
            startActivity(intent)
        }

        loadPemilih()

        checkLoginStatus()

        binding.logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun loadPemilih() {
        GlobalScope.launch(Dispatchers.Main) {
            val pemilih = db.PemilihDao().getAllPemilih()
            pemilihList.clear()
            pemilihList.addAll(pemilih)
            adapter.notifyDataSetChanged()
        }
    }

    private fun checkLoginStatus() {
        val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)

        if (isLoggedIn) {
            Toast.makeText(this, "Berhasil login!", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun logout() {
        val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("is_logged_in", false)
        editor.apply()

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            loadPemilih()
        }
    }

    companion object {
        const val EDIT_REQUEST_CODE = 1
    }
}
