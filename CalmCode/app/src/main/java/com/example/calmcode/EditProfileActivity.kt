package com.example.calmcode

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.calmcode.app.calmcodeApplication
import com.example.calmcode.utils.getString
import com.example.calmcode.utils.isNotValid
import com.example.calmcode.utils.toast

class EditProfileActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val etUsername = findViewById<EditText>(R.id.edittext_username)
        etUsername.setText((application as calmcodeApplication).getUsername())
        val etPass = findViewById<EditText>(R.id.edittext_password)
        etPass.setText((application as calmcodeApplication).getPassword())
        val etEmail = findViewById<EditText>(R.id.edittext_email)
        etEmail.setText((application as calmcodeApplication).getEmail())

        val button_profile = findViewById<Button>(R.id.button_profile)
        button_profile.setOnClickListener {
            if(etUsername.isNotValid() || etPass.isNotValid() || etEmail.isNotValid()){
                toast("Please Fill in all entries")
                return@setOnClickListener
            }
            (application as calmcodeApplication).setUsername(etUsername.getString())
            (application as calmcodeApplication).setPassword(etPass.getString())
            (application as calmcodeApplication).setEmail(etEmail.getString())
            toast("Saved!")
        }
    }
}