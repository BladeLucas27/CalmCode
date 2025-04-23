package com.example.calmcode

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class DevelopersActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developers)

        val button_settings = findViewById<Button>(R.id.button_settings)
        button_settings.setOnClickListener {
            Toast.makeText(this, "Back to Settings", Toast.LENGTH_LONG).show()
            val intent = Intent(this, SettingsFragment::class.java)
            startActivity(intent)
            finish()
        }
    }
}