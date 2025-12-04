package com.hilmi.aplikasipertama

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.isEmpty
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout

class FormulirActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var switchMode: SwitchMaterial
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_formulir)

        setupToolbarDanDrawer()
        setupNightModeSwitch()

        val nama = findViewById<TextInputLayout>(R.id.etNamaLengkap)
        val alamat = findViewById<TextInputLayout>(R.id.etAlamatLengkap)
        val nohp = findViewById<TextInputLayout>(R.id.etNoHP)
        val rbGender = findViewById<RadioGroup>(R.id.rbJenisKelamin)
        val spinner = findViewById<Spinner>(R.id.spinnerAgama)
        val cbMembaca = findViewById<CheckBox>(R.id.cbMembaca)
        val cbMenulis = findViewById<CheckBox>(R.id.cbMenulis)
        val cbBelajar = findViewById<CheckBox>(R.id.cbBelajar)
        val cbOlahraga = findViewById<CheckBox>(R.id.cbOlahraga)
        val kirim = findViewById<Button>(R.id.btnKirim)

        kirim.setOnClickListener {
            val inputNama = nama.editText?.text.toString()
            val inputAlamat = alamat.editText?.text.toString()
            val inputNohp = nohp.editText?.text.toString()

            val selectedRadioId = rbGender.checkedRadioButtonId
            val inputGender = if (selectedRadioId != -1){
                findViewById<RadioButton>(selectedRadioId).text.toString()
            } else { "" }

            val inputSpinner = spinner.selectedItem.toString()

            val hobiList = mutableListOf<String>()
            if (cbMembaca.isChecked){
                hobiList.add("Membaca")
            }
            if (cbMenulis.isChecked){
                hobiList.add("Menulis")
            }
            if (cbBelajar.isChecked){
                hobiList.add("Belajar")
            }
            if (cbOlahraga.isChecked){
                hobiList.add("Olahraga")
            }
            val inputHobi = hobiList.joinToString(", ")

            if (inputNama.isEmpty() || inputAlamat.isEmpty() || inputNohp.isEmpty() || inputHobi.isEmpty() || inputGender.isEmpty() || inputSpinner.isEmpty()){
                Toast.makeText(this@FormulirActivity,"Lengkapi Semua Data!", Toast.LENGTH_SHORT)
            } else {

                val intent = Intent(this, HasilFormulirActivity::class.java)
                intent.putExtra("NAMA", inputNama)
                intent.putExtra("ALAMAT", inputAlamat)
                intent.putExtra("NOHP", inputNohp)
                intent.putExtra("GENDER", inputGender)
                intent.putExtra("AGAMA", inputSpinner)
                intent.putExtra("HOBI", inputHobi)
                startActivity(intent)
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