package com.example.calmcode.app

import android.app.Application
import android.util.Log

class myApplication : Application(){
    var username : String = ""
    var password : String = ""
    var email : String = ""

    override fun onCreate() {
        super.onCreate()
        Log.e("Application Class Practice","MyApplication is called")
    }
}