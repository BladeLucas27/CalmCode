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
import com.example.calmcode.app.myApplication
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val edittext_username = findViewById<EditText>(R.id.edittext_username)
        val edittext_password = findViewById<EditText>(R.id.edittext_password)
        val edittext_confirm_password = findViewById<EditText>(R.id.edittext_confirm_password)
        val button_register = findViewById<Button>(R.id.button_register)

        button_register.setOnClickListener {
            val username = edittext_username.text.toString().trim()
            val password = edittext_password.text.toString().trim()
            val confirm = edittext_confirm_password.text.toString().trim()
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username and Password cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (!isValidPassword(password)) {
                Toast.makeText(this, "Password must be 8-16 characters long, include one uppercase letter, one special character, and one number.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(confirm == password){
                Log.e("CalmCode", "button is clicked")

                val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("username", username)
                editor.putString("password", password)
                editor.apply()

//                (application as myApplication).setUsername(username)
//                (application as myApplication).setPassword(password)

                Toast.makeText(this, "Account Created Successfully!", Toast.LENGTH_LONG).show()

                // goto login
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_LONG).show()
            }

        }
    }
    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = Pattern.compile(
            "^(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=.*[0-9]).{8,16}$"
        )
        return passwordPattern.matcher(password).matches()
    }
}