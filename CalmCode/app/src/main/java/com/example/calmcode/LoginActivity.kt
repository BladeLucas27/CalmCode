package com.example.calmcode

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        val button_register = findViewById<Button>(R.id.button_register)
//        button_register.setOnClickListener {
//            Log.e("Register Button", "Button is clicked")
//
//            Toast.makeText(this, "I am clicked!", Toast.LENGTH_LONG).show()
//            val intent = Intent(this, ProfileActivity::class.java)
//            startActivity(intent)
//        }

        val edittext_username = findViewById<EditText>(R.id.edittext_username)
        val edittext_password = findViewById<EditText>(R.id.edittext_password)
        val button_login = findViewById<Button>(R.id.button_login)
        val button_register = findViewById<Button>(R.id.button_register)
        val togglePassword = findViewById<ImageView>(R.id.toggle_password)

        //password visibility
        var isPasswordVisible = false
        edittext_password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        togglePassword.setImageResource(R.drawable.eye_1)
        togglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                edittext_password.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                togglePassword.setImageResource(R.drawable.eye_1_2)
            } else {
                edittext_password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                togglePassword.setImageResource(R.drawable.eye_1)
            }
            edittext_password.setSelection(edittext_password.text.length)
        }

        button_login.setOnClickListener{
            val username = edittext_username.text.toString().trim()
            val password = edittext_password.text.toString().trim()

            if(username.isNullOrEmpty() || password.isNullOrEmpty()){
                Toast.makeText(this, "Username and Password cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            //this line will not be executed if this is not true

            //validation
            val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val savedUsername = sharedPreferences.getString("username", null)
            val savedPassword = sharedPreferences.getString("password", null)

            if (savedUsername == null || savedPassword == null) {
                Toast.makeText(this, "No account found. Please register first.", Toast.LENGTH_LONG).show()
            } else if (username == savedUsername && password == savedPassword) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_LONG).show()
            }
        }
        button_register.setOnClickListener {
            Log.e("CalmCode", "button is clicked")
            Toast.makeText(this, "Opening register screen", Toast.LENGTH_LONG).show()
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}