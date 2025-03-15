package com.example.calmcode

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.util.Log
import android.widget.ImageButton

class SettingsActivity() : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val button_profile = findViewById<ImageButton>(R.id.button_profile)
        button_profile.setOnClickListener {
            Toast.makeText(this, "Back to profile", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val blueTheme = findViewById<Button>(R.id.button_theme_blue)
        blueTheme.setOnClickListener {
            Log.e("Register Button", "Button is clicked")

            Toast.makeText(this, "Changed theme to blue", Toast.LENGTH_LONG).show()
        }

        val greenTheme = findViewById<Button>(R.id.button_theme_green)
        greenTheme.setOnClickListener {
            Log.e("Register Button", "Button is clicked")

            Toast.makeText(this, "Changed theme to green", Toast.LENGTH_LONG).show()
        }

        val pinkTheme = findViewById<Button>(R.id.button_theme_pink)
        pinkTheme.setOnClickListener {
            Log.e("Register Button", "Button is clicked")

            Toast.makeText(this, "Changed theme to pink", Toast.LENGTH_LONG).show()
        }

        val button_developers = findViewById<Button>(R.id.button_developers)
        button_developers.setOnClickListener {
            Toast.makeText(this, "Here we are!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, DevelopersActivity::class.java)
            startActivity(intent)
        }

    }
}