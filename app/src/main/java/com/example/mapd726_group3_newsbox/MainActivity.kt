package com.example.mapd726_group3_newsbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logoBtn = findViewById<Button>(R.id.profile_btn)
        logoBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, SignIn::class.java)
            startActivity(intent)
        }
    }

}