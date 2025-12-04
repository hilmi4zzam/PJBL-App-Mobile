package com.hilmi.aplikasipertama

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.switchmaterial.SwitchMaterial

class HasilFormulirActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hasil_formulir_fragment)

        val nama = findViewById<TextView>(R.id.Nama)
        val alamat = findViewById<TextView>(R.id.Alamat)
        val nomor = findViewById<TextView>(R.id.Nomor)
        val agama = findViewById<TextView>(R.id.Agama)
        val jenisKelamin = findViewById<TextView>(R.id.JenisKelamin)
        val kegemaran = findViewById<TextView>(R.id.Kegemaran)

        val inputNama = intent.getStringExtra("NAMA")
        val inputAlamat = intent.getStringExtra("ALAMAT")
        val inputNohp = intent.getStringExtra("NOHP")
        val inputGender = intent.getStringExtra("GENDER")
        val inputSpinner = intent.getStringExtra("AGAMA")
        val inputHobi = intent.getStringExtra("HOBI")

        nama.text = "Nama : $inputNama"
        alamat.text = "Alamat : $inputAlamat"
        nomor.text = "Nomor Telepon : $inputNohp"
        agama.text = "Agama : $inputSpinner"
        jenisKelamin.text = "Jenis Kelamin : $inputGender"
        kegemaran.text = "Kegemaran : $inputHobi"
    }
}