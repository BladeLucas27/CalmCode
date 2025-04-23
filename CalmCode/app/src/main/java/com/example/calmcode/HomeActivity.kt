package com.example.calmcode

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_home)


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment())
                        .commit()
                    true
                }
                R.id.nav_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment())
                        .commit()
                    true
                }
                R.id.nav_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, SettingsFragment())
                        .commit()
                    true
                }
                else -> false
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

