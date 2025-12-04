package com.hilmi.aplikasipertama

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout

class WarungActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var switchMode: SwitchMaterial
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_warung)

        setupToolbarDanDrawer()
        setupNightModeSwitch()

        val etMakanan1 = findViewById<EditText>(R.id.jumlahNasiPedas)
        val etMakanan2 = findViewById<EditText>(R.id.jumlahNasiCampur)
        val etMinuman1 = findViewById<EditText>(R.id.jumlahEsTeh)
        val etMinuman2 = findViewById<EditText>(R.id.jumlahEsBuah)
        val etPemesan = findViewById<TextInputLayout>(R.id.etPemesan)

        setupPenambahan(findViewById(R.id.tombolMinusMakanan1), findViewById(R.id.tombolPlusMakanan1), etMakanan1)
        setupPenambahan(findViewById(R.id.tombolMinusMakanan2), findViewById(R.id.tombolPlusMakanan2), etMakanan2)
        setupPenambahan(findViewById(R.id.tombolMinusMinuman1), findViewById(R.id.tombolPlusMinuman1), etMinuman1)
        setupPenambahan(findViewById(R.id.tombolMinusMinuman2), findViewById(R.id.tombolPlusMinuman2), etMinuman2)

        findViewById<Button>(R.id.btnHitung).setOnClickListener {
            val jumlahMakanan1 = etMakanan1.text.toString().toIntOrNull() ?: 0
            val jumlahMakanan2 = etMakanan2.text.toString().toIntOrNull() ?: 0
            val jumlahMinuman1 = etMinuman1.text.toString().toIntOrNull() ?: 0
            val jumlahMinuman2 = etMinuman2.text.toString().toIntOrNull() ?: 0

            val hargaMakanan1 = 15000
            val hargaMakanan2 = 23000
            val hargaMinuman1 = 4000
            val hargaMinuman2 = 10000

            var totalHarga = 0
            val detailPesanan = StringBuilder()

            if (jumlahMakanan1 > 0) {
                val subtotal = jumlahMakanan1 * hargaMakanan1
                totalHarga += subtotal
                detailPesanan.append("Nasi Goreng ($jumlahMakanan1 x $hargaMakanan1) = $subtotal\n")
            }
            if (jumlahMakanan2 > 0) {
                val subtotal = jumlahMakanan2 * hargaMakanan2
                totalHarga += subtotal
                detailPesanan.append("Nasi Campur ($jumlahMakanan2 x $hargaMakanan2) = $subtotal\n")
            }
            if (jumlahMinuman1 > 0) {
                val subtotal = jumlahMinuman1 * hargaMinuman1
                totalHarga += subtotal
                detailPesanan.append("Es Teh ($jumlahMinuman1 x $hargaMinuman1) = $subtotal\n")
            }
            if (jumlahMinuman2 > 0) {
                val subtotal = jumlahMinuman2 * hargaMinuman2
                totalHarga += subtotal
                detailPesanan.append("Es Buah ($jumlahMinuman2 x $hargaMinuman2) = $subtotal\n")
            }
            
            val namaPemesan = etPemesan.editText?.text.toString()

            val intent = Intent(this, NotaActivity::class.java)
            intent.putExtra("NAMA_PEMESAN", namaPemesan)
            intent.putExtra("DETAIL_PESANAN", detailPesanan.toString())
            intent.putExtra("TOTAL_HARGA", totalHarga)
            startActivity(intent)
        }
    }

    private fun setupPenambahan(btnMinus: ImageButton, btnPlus: ImageButton, editText: EditText) {
        btnPlus.setOnClickListener {
            val currentVal = editText.text.toString().toIntOrNull() ?: 0
            editText.setText((currentVal + 1).toString())
        }
        btnMinus.setOnClickListener {
            val currentVal = editText.text.toString().toIntOrNull() ?: 0
            if (currentVal > 0) {
                editText.setText((currentVal - 1).toString())
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