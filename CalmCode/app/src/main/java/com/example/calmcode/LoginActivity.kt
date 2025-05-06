package com.example.calmcode

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calmcode.app.calmcodeApplication
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val edittext_username = findViewById<TextInputEditText>(R.id.tfUsername)
        val edittext_password = findViewById<TextInputEditText>(R.id.tfPassword)
        val button_login = findViewById<Button>(R.id.button_login)
        val button_register = findViewById<Button>(R.id.button_register)

        edittext_username.setText((application as calmcodeApplication).getUsername())
        edittext_password.setText((application as calmcodeApplication).getPassword())

        button_login.setOnClickListener{
            Log.e("CalmCode", ":Login button is clicked")
            val username = edittext_username.text.toString().trim()
            val password = edittext_password.text.toString().trim()

            if(username.isNullOrEmpty() || password.isNullOrEmpty()){
                Toast.makeText(this, "Username and Password cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            //this line will not be executed if this is not true

            //validation
            val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val savedUsername = sharedPreferences.getString("username", null)
            val savedPassword = sharedPreferences.getString("password", null)

            if (savedUsername == null || savedPassword == null) {
                Toast.makeText(this, "No account found. Please register first.", Toast.LENGTH_LONG).show()
            } else if (username == savedUsername && password == savedPassword) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_LONG).show()
            }
        }
        button_register.setOnClickListener {
            Log.e("CalmCode", "button is clicked")
            Toast.makeText(this, "Opening register screen", Toast.LENGTH_LONG).show()
            val intent = Intent(    this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}