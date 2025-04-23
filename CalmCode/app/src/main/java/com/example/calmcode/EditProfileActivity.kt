package com.example.calmcode

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.res.stringResource
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calmcode.app.myApplication
import com.example.calmcode.utils.getString
import com.example.calmcode.utils.isNotValid
import com.example.calmcode.utils.toast

class EditProfileActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val etUsername = findViewById<EditText>(R.id.edittext_username)
        etUsername.setText((application as myApplication).getUsername())
        val etPass = findViewById<EditText>(R.id.edittext_password)
        etPass.setText((application as myApplication).getPassword())
        val etEmail = findViewById<EditText>(R.id.edittext_email)
        etEmail.setText((application as myApplication).getEmail())

        val button_profile = findViewById<Button>(R.id.button_profile)
        button_profile.setOnClickListener {
            if(etUsername.isNotValid() || etPass.isNotValid() || etEmail.isNotValid()){
                toast("Please Fill in all entries")
                return@setOnClickListener
            }
            (application as myApplication).setUsername(etUsername.getString())
            (application as myApplication).setPassword(etPass.getString())
            (application as myApplication).setEmail(etEmail.getString())
            toast("Saved!")
//            val intent = Intent(this, ProfileActivity::class.java)
//
//            startActivity(intent)
//            finish()
        }
    }
}