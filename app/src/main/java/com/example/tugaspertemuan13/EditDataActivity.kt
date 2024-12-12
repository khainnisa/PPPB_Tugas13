package com.example.tugaspertemuan13

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.tugaspertemuan13.databinding.ActivityEditDataBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDataBinding
    private lateinit var db: PemilihDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(applicationContext, PemilihDatabase::class.java, "pemilih-db")
            .build()

        val pemilihId = intent.getIntExtra("ID", 0)
        val name = intent.getStringExtra("NAME") ?: ""
        val nik = intent.getStringExtra("NIK") ?: ""
        val gender = intent.getStringExtra("GENDER") ?: ""
        val address = intent.getStringExtra("ADDRESS") ?: ""

        binding.editNameEditText.setText(name)
        binding.editNikEditText.setText(nik)
        binding.editGenderRadioGroup.check(
            if (gender == "Laki-Laki") R.id.editMaleRadioButton else R.id.editFemaleRadioButton
        )
        binding.editAddressEditText.setText(address)

        binding.editSaveButton.setOnClickListener {
            val updatedName = binding.editNameEditText.text.toString().trim()
            val updatedNik = binding.editNikEditText.text.toString().trim()
            val updatedGender = when (binding.editGenderRadioGroup.checkedRadioButtonId) {
                R.id.editMaleRadioButton -> "Laki-Laki"
                R.id.editFemaleRadioButton -> "Perempuan"
                else -> ""
            }
            val updatedAddress = binding.editAddressEditText.text.toString().trim()

            if (updatedName.isEmpty() || updatedNik.isEmpty() || updatedGender.isEmpty() || updatedAddress.isEmpty()) {
                Toast.makeText(this, "Harap isi semua data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedPemilih = Pemilih(
                id = pemilihId,
                name = updatedName,
                nik = updatedNik,
                gender = updatedGender,
                address = updatedAddress
            )
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    db. PemilihDao().updatePemilih(updatedPemilih)
                    Toast.makeText(this@EditDataActivity, "Data berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK)
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@EditDataActivity, "Gagal memperbarui data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}