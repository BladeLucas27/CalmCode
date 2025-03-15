package com.example.calmcode

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button_register = findViewById<Button>(R.id.button_register)
        button_register.setOnClickListener {
            Log.e("Register Button", "Button is clicked")

            Toast.makeText(this, "I am clicked!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}