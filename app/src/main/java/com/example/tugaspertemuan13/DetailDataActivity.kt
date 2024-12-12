package com.example.tugaspertemuan13

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tugaspertemuan13.databinding.ActivityDetailDataBinding

class DetailDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("NAME")
        val nik = intent.getStringExtra("NIK")
        val gender = intent.getStringExtra("GENDER")
        val address = intent.getStringExtra("ADDRESS")

        binding.detailName.setText("Nama: $name")
        binding.detailNik.setText("NIK: $nik")
        binding.detailGenderEditText.setText("Gender: $gender")
        binding.detailAddress.setText("Alamat: $address")

        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}
