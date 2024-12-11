package com.example.tugaspertemuan13

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.tugaspertemuan13.databinding.ActivityAddDataBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddDataBinding
    private lateinit var db: PemilihDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(applicationContext, PemilihDatabase::class.java, "pemilih-db")
            .build()

        binding.addSaveButton.setOnClickListener {
            val name = binding.addNameEditText.text.toString().trim()
            val nik = binding.addNikEditText.text.toString().trim()
            val gender = when (binding.addGenderRadioGroup.checkedRadioButtonId) {
                R.id.addMaleRadioButton -> "Laki-Laki"
                R.id.addFemaleRadioButton -> "Perempuan"
                else -> ""
            }
            val address = binding.addAddressEditText.text.toString().trim()

            if (name.isEmpty() || nik.isEmpty() || gender.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Harap isi semua data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newPemilih = Pemilih(name = name, nik = nik, gender = gender, address = address)
            GlobalScope.launch(Dispatchers.Main) {
                db.PemilihDao().insertPemilih(newPemilih)
                Toast.makeText(this@AddDataActivity, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@AddDataActivity, MainActivity::class.java) // Mengarahkan ke MainActivity
                startActivity(intent)
                finish()
            }
        }
    }
}
