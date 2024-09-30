package com.example.dynamicui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.example.dynamicui.R
import com.example.dynamicui.ui.RegistrationActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val registerButton: Button = findViewById(R.id.registerButton)
        registerButton.setOnClickListener {
            // Start RegistrationActivity when button is clicked
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}