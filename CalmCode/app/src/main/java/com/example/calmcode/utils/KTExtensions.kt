package com.example.calmcode.utils

import android.app.Activity
import android.widget.EditText
import android.widget.Toast

fun Activity.toast(toastMessage : String){
    Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
}

fun EditText.getString() : String = this.text.toString()

fun EditText.isNotValid() : Boolean = this.getString().isNullOrEmpty()