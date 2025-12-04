package com.hilmi.aplikasipertama

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.switchmaterial.SwitchMaterial
import java.text.DecimalFormat

class KalkulatorActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var switchMode: SwitchMaterial
    private lateinit var navigationView: NavigationView
    private lateinit var tvDisplay: EditText

    // Variabel Logika
    private var nilaiPertama: Double = 0.0
    private var operasi: String = ""
    private var isNewOp: Boolean = true // Penanda apakah kita baru saja menekan tombol operasi


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_kalkulator)

        setupToolbarDanDrawer()
        setupNightModeSwitch()

        // Inisialisasi EditText
        tvDisplay = findViewById(R.id.tvDisplay)

        // Mematikan keyboard bawaan agar tidak muncul saat EditText diklik
        tvDisplay.showSoftInputOnFocus = false

        // --- Inisialisasi Tombol Angka (0-9) ---
        val buttonsNumber = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        buttonsNumber.forEach { id ->
            findViewById<MaterialButton>(id).setOnClickListener { view ->
                onNumberClick(view)
            }
        }

        // --- Inisialisasi Tombol Operasi ---
        findViewById<MaterialButton>(R.id.btnTambah).setOnClickListener { onOperatorClick("+") }
        findViewById<MaterialButton>(R.id.btnKurang).setOnClickListener { onOperatorClick("-") }
        findViewById<MaterialButton>(R.id.btnKali).setOnClickListener { onOperatorClick("*") }
        findViewById<MaterialButton>(R.id.btnBagi).setOnClickListener { onOperatorClick("/") }

        // --- Tombol Fungsi Lainnya ---
        findViewById<MaterialButton>(R.id.btnEqual).setOnClickListener { onEqualClick() }
        findViewById<MaterialButton>(R.id.btnAC).setOnClickListener { onClearClick() }
        findViewById<MaterialButton>(R.id.btnDelete).setOnClickListener { onDeleteClick() }

        // --- LOGIKA DECIMAL (TITIK) ---
        findViewById<MaterialButton>(R.id.btnTitik).setOnClickListener {
            val textSekarang = tvDisplay.text.toString()

            // Jika baru mulai operasi, langsung jadi "0."
            if (isNewOp) {
                tvDisplay.setText("0.")
                isNewOp = false
            }
            // Cek agar tidak ada double titik (contoh: 1.2.5 dilarang)
            else if (!textSekarang.contains(".")) {
                tvDisplay.append(".")
            }
        }
    }

    // Fungsi saat tombol angka ditekan
    private fun onNumberClick(view: View) {
        val button = view as MaterialButton
        val angka = button.text.toString()

        if (isNewOp) {
            tvDisplay.setText(angka)
            isNewOp = false
        } else {
            // Mencegah angka 0 di awal berulang (misal 00001) kecuali desimal
            if (tvDisplay.text.toString() == "0") {
                tvDisplay.setText(angka)
            } else {
                tvDisplay.append(angka)
            }
        }
    }

    // Fungsi saat tombol operasi (+ - * /) ditekan
    private fun onOperatorClick(op: String) {
        try {
            operasi = op
            nilaiPertama = tvDisplay.text.toString().toDouble()
            isNewOp = true // Set flag agar input angka berikutnya me-reset layar
        } catch (e: NumberFormatException) {
            // Handle jika layar kosong
        }
    }

    // Fungsi Hitung (=)
    private fun onEqualClick() {
        try {
            val nilaiKedua = tvDisplay.text.toString().toDouble()
            var hasil = 0.0

            when (operasi) {
                "+" -> hasil = nilaiPertama + nilaiKedua
                "-" -> hasil = nilaiPertama - nilaiKedua
                "*" -> hasil = nilaiPertama * nilaiKedua
                "/" -> {
                    if (nilaiKedua != 0.0) {
                        hasil = nilaiPertama / nilaiKedua
                    } else {
                        tvDisplay.setText("Error")
                        isNewOp = true
                        return
                    }
                }
            }

            // Format hasil agar menghapus .0 jika bilangan bulat (contoh: 2.0 jadi 2)
            val format = DecimalFormat("0.##########")
            tvDisplay.setText(format.format(hasil))
            isNewOp = true // Siap untuk operasi baru dari hasil ini

        } catch (e: Exception) {
            // Error handling ringan
        }
    }

    // Fungsi AC (All Clear)
    private fun onClearClick() {
        tvDisplay.setText("0")
        nilaiPertama = 0.0
        operasi = ""
        isNewOp = true
    }

    // Fungsi Delete (Hapus satu karakter)
    private fun onDeleteClick() {
        // Jangan hapus jika sedang menampilkan hasil perhitungan baru
        if (isNewOp) {
            tvDisplay.setText("0")
            return
        }

        val text = tvDisplay.text.toString()
        if (text.length > 1) {
            tvDisplay.setText(text.substring(0, text.length - 1))
        } else {
            tvDisplay.setText("0")
            isNewOp = true
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