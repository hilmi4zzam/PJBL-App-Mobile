package com.hilmi.aplikasipertama

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.switchmaterial.SwitchMaterial

class MauBuatDrawer : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var switchMode: SwitchMaterial
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.mau_buat_drawer)

        setupToolbarDanDrawer()
        setupNightModeSwitch()
        setupTombolMenu()
        setupOnBackPressed()

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
                switchMode.text = getString(R.string.dark_mode)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchMode.text = getString(R.string.light_mode)
            }
        }
    }

    private fun setupTombolMenu() {

        val formulir: CardView = findViewById(R.id.cvFormulir)
        val kalkulator: CardView = findViewById(R.id.cvKalkulator)
        val warung: CardView = findViewById(R.id.cvWarung)
        val konversi: CardView = findViewById(R.id.cvKonversi)
        val profil: CardView = findViewById(R.id.cvProfil)

        formulir.setOnClickListener {
            startActivity(Intent(this, FormulirActivity::class.java))
        }
        kalkulator.setOnClickListener {
            startActivity(Intent(this, KalkulatorActivity::class.java))
        }
        warung.setOnClickListener {
            startActivity(Intent(this, WarungActivity::class.java))
        }
        konversi.setOnClickListener {
            startActivity(Intent(this, KonversiActivity::class.java))
        }
        profil.setOnClickListener {
            startActivity(Intent(this, ProfilActivity::class.java))
        }
    }

    private fun setupOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    AlertDialog.Builder(this@MauBuatDrawer)
                        .setTitle("hlmzProject.")
                        .setMessage("Apakah anda yakin ingin keluar dari projek hilmi?")
                        .setPositiveButton("Ya") { _, _ ->
                            finishAffinity()
                        }
                        .setNegativeButton("Tidak", null)
                        .show()
                }
            }
        })
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
}