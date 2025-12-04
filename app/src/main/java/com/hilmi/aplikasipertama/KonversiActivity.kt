package com.hilmi.aplikasipertama

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
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

class KonversiActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var switchMode: SwitchMaterial
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_konversi)

        setupNightModeSwitch()
        setupToolbarDanDrawer()

        val spinnerFrom: Spinner = findViewById(R.id.spinnerFrom)
        val spinnerTo: Spinner = findViewById(R.id.spinnerTo)
        val input: EditText = findViewById(R.id.etTempFrom)
        val output: TextView = findViewById(R.id.etTempTo)
        val button: Button = findViewById(R.id.btnConvert)

        var hasil = 0.0

        button.setOnClickListener {

            val suhuAwal = spinnerFrom.selectedItem.toString()
            val suhuAkhir = spinnerTo.selectedItem.toString()
            val angkaSuhuAwal = input.text.toString().toDoubleOrNull()

            if (angkaSuhuAwal != null) {

                if (suhuAwal == "Celcius") {
                    if (suhuAkhir == "Kelvin") {
                        hasil = angkaSuhuAwal + 273.15
                    }
                    if (suhuAkhir == "Farenhait") {
                        hasil = (angkaSuhuAwal * 1.8) + 32
                    }
                    if (suhuAkhir == "Celcius") {
                        hasil = angkaSuhuAwal
                    }
                    output.text = hasil.toString()
                }

                if (suhuAwal == "Farenhait") {
                    if (suhuAkhir == "Celcius") {
                        hasil = (angkaSuhuAwal - 32 ) / 1.8
                    }
                    if (suhuAkhir == "Farenhait") {
                        hasil = angkaSuhuAwal
                    }
                    if (suhuAkhir == "Kelvin") {
                        hasil = angkaSuhuAwal - 32 / 1.8 + 273.15
                    }
                    output.text = hasil.toString()
                }

                if (suhuAwal == "Kelvin") {
                    if (suhuAkhir == "Kelvin") {
                        hasil = angkaSuhuAwal
                    }
                    if (suhuAkhir == "Farenhait") {
                        hasil = (angkaSuhuAwal - 273.15) * 1.8 + 32
                    }
                    if (suhuAkhir == "Celcius") {
                        hasil = angkaSuhuAwal - 273.15
                    }
                    output.text = hasil.toString()
                }

            } else {
                input.error = "Masukkan Angka nya Dulu ya"

                Handler(Looper.getMainLooper()).postDelayed({
                    input.error = null
                }, 2000)
            }
        }
    }

    private fun setupToolbarDanDrawer() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setupNightModeSwitch() {
        switchMode = findViewById(R.id.switchMode)
        switchMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchMode.text = "Mode Gelap"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchMode.text = "Mode Terang"
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_formulir -> startActivity(Intent(this, FormulirActivity::class.java))
            R.id.nav_kalkulator -> startActivity(Intent(this, KalkulatorActivity::class.java))
            R.id.nav_warung -> startActivity(Intent(this, WarungActivity::class.java))
            R.id.nav_konversi -> startActivity(Intent(this, KonversiActivity::class.java))
            R.id.nav_profil -> startActivity(Intent(this, ProfilActivity::class.java))
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    @SuppressLint("MissingSuperCall", "GestureBackNavigation")
    override fun onBackPressed() {
        // 1. Jika Drawer terbuka -> Tutup Drawer
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        // 2. Jika Drawer tertutup -> Biarkan sistem menangani Back (kembali ke fragment sebelumnya/keluar)
        else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

}