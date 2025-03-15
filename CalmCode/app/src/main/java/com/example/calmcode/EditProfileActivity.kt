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
import com.example.calmcode.utils.isNotValid
import com.example.calmcode.utils.toast

class EditProfileActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val editUsername = findViewById<EditText>(R.id.edittext_username)
        editUsername.setText((application as myApplication).username)
        val editPassword = findViewById<EditText>(R.id.edittext_password)
        editPassword.setText((application as myApplication).password)
        val editEmail = findViewById<EditText>(R.id.edittext_email)
        editEmail.setText((application as myApplication).email)

        val button_profile = findViewById<Button>(R.id.button_profile)
        button_profile.setOnClickListener {
            if(editUsername.isNotValid() || editPassword.isNotValid() || editEmail.isNotValid()){
                toast("Don't leave any entries blank")
                return@setOnClickListener
            }

            (application as myApplication).username = editUsername.toString()
            (application as myApplication).password = editPassword.toString()
            (application as myApplication).email = editEmail.toString()

            toast("Saved!")

            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}