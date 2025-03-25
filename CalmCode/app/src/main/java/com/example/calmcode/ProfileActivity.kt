package com.example.calmcode

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calmcode.app.myApplication
import com.example.calmcode.utils.toast

class ProfileActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val tvUsername = findViewById<TextView>(R.id.tv_username)
        val tvEmail = findViewById<TextView>(R.id.tv_email)
        tvUsername.setText((application as myApplication).getUsername())
        tvEmail.setText((application as myApplication).getEmail())

        val button_settings = findViewById<ImageButton>(R.id.button_settings)
        button_settings.setOnClickListener {
            toast("Going to Settings")
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        val button_editprofile = findViewById<Button>(R.id.button_editprofile)
        button_editprofile.setOnClickListener {
            toast("Editing Profile")
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}