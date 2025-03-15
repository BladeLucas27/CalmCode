package com.example.calmcode.utils

import android.app.Activity
import android.widget.EditText
import android.widget.Toast

fun Activity.toast(toastMessage : String){
    Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show()
}

fun EditText.isNotValid(): Boolean{
    return this.text.toString().isNullOrEmpty()
}