package com.example.calmcode

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnDaily = findViewById<Button>(R.id.btnDaily)
        val btnCourse = findViewById<Button>(R.id.btnCourse)
        val btnFavorite = findViewById<Button>(R.id.btnFavorite)
        val btnDownloads = findViewById<Button>(R.id.btnDownloads)
        val btnCommunity = findViewById<Button>(R.id.btnCommunity)
        val btnWorkshop = findViewById<Button>(R.id.btnWorkshop)
        val btnCoaching = findViewById<Button>(R.id.btnCoaching)
        val btnPlans = findViewById<Button>(R.id.btnPlans)
    }
}