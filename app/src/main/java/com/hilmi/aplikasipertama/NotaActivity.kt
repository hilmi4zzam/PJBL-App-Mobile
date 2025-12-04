package com.hilmi.aplikasipertama

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.switchmaterial.SwitchMaterial

class NotaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nota)

        val tvNamaPemesan = findViewById<TextView>(R.id.tvNamaPemesan)
        val tvRincianPesanan = findViewById<TextView>(R.id.tvDetailPesanan)
        val tvTotalBayar = findViewById<TextView>(R.id.tvTotalBayar)

        val namaPemesan = intent.getStringExtra("NAMA_PEMESAN") ?: "-"
        val detailPesanan = intent.getStringExtra("DETAIL_PESANAN") ?: ""
        val totalHarga = intent.getIntExtra("TOTAL_HARGA", 0)

        tvNamaPemesan.text = "Nama Pemesan: $namaPemesan"
        tvRincianPesanan.text = detailPesanan
        tvTotalBayar.text = "Total: Rp $totalHarga"

    }


}