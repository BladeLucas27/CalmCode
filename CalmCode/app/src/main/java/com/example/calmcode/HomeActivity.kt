package com.example.calmcode

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.calmcode.utils.toast
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.activity_home)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.drawer_open, R.string.drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.nav_settings -> Intent(this, SettingsActivity::class.java).apply {
                    toast("Settings")
                    startActivity(this)
                    finish()
                }
                R.id.nav_profile -> Intent(this, ProfileActivity::class.java).apply {
                    toast("Profile")
                    startActivity(this)
                    finish()
                }
                R.id.nav_about -> Intent(this, DevelopersActivity::class.java).apply {
                    toast("About Us")
                    startActivity(this)
                    finish()
                }
                R.id.nav_logout -> {
                    toast("Exiting")
                    finish()
                }
            }
            drawerLayout.closeDrawers()
            true
        }

        val btnDaily = findViewById<Button>(R.id.btnDaily)
        val btnCourse = findViewById<Button>(R.id.btnCourse)
        val btnFavorite = findViewById<Button>(R.id.btnFavorite)
        btnFavorite.setOnClickListener {
            toast("Going to Music Selection")
            intent = Intent(this, MusicGenresActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnDownloads = findViewById<Button>(R.id.btnDownloads)
        btnDownloads.setOnClickListener {
            toast("Going to Music Downloads Selection")
            intent = Intent(this, MusicDownloadsActivity::class.java)
            startActivity(intent)
            finish()
        }
        val btnCommunity = findViewById<Button>(R.id.btnCommunity)
        val btnWorkshop = findViewById<Button>(R.id.btnWorkshop)
        val btnCoaching = findViewById<Button>(R.id.btnCoaching)
        val btnPlans = findViewById<Button>(R.id.btnPlans)

        btnDaily.setOnClickListener {
            Toast.makeText(this, "Opening Genre Selection screen", Toast.LENGTH_LONG).show()
            val intent = Intent(    this,MusicGenresActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}