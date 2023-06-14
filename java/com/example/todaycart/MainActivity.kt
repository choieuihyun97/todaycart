package com.example.todaycart


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 가나다라
        val btnLogin : Button = findViewById(R.id.btnLogin)
        val btnJoin : Button = findViewById(R.id.btnJoin)
        val rc : RecyclerView = findViewById(R.id.rc1)
        val members : MutableList<MemberVO> = mutableListOf()

        btnLogin.setOnClickListener {
            val intent = Intent(this@MainActivity,LoginActivity::class.java)
            startActivity(intent)
        }
        btnJoin.setOnClickListener {
            val intent = Intent(this@MainActivity,JoinActivity::class.java)
            startActivity(intent)
        }
        members.add(MemberVO(R.drawable.ad))
        val adapter = MemberAdapter(applicationContext,R.layout.ad_list,members)
        rc.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
        rc.adapter = adapter
    }
}