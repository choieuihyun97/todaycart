package com.example.todaycart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PaySuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_success)

        val btnHome : Button = findViewById(R.id.btnHome)

        btnHome.setOnClickListener {
            val intent = Intent(this@PaySuccessActivity, Main_AfterActivity::class.java)
            startActivity(intent)

        }
    }

}