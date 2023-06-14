package com.example.todaycart

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class Main_AfterActivity : AppCompatActivity() {
    lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_after)
        val tvLogin: TextView = findViewById(R.id.tvLogin)
        val btnBucket: Button = findViewById(R.id.btnBucket)
        val btnSearch: Button = findViewById(R.id.btnSearch)
        val btnMap: Button = findViewById(R.id.btnMap)
        val btnCall: Button = findViewById(R.id.btnCall)
        queue = Volley.newRequestQueue(this)

        val sf2 = this.getSharedPreferences("login", Context.MODE_PRIVATE)


        val id = sf2.getString("member_id", "null")
        val pw = sf2.getString("member_pw", "null")




        if (id != null && pw != null) {

            tvLogin.text = "${id}님 환영합니다 \n 현재 cart01번 카트 이용중입니다."
        } else {
            Toast.makeText(this, "ID/PW를 확인해주세요", Toast.LENGTH_SHORT).show()
        }
        btnBucket.setOnClickListener {
            val intent = Intent(this@Main_AfterActivity, ShoppingActivity::class.java)

            startActivity(intent)
        }

        btnMap.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        btnSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        btnCall.setOnClickListener {
            val intent = Intent(this, CallActivity::class.java)
            val url = "http://119.200.31.135:9090/project/callManager"
            val params = JSONObject()
            params.put("Content-Type", "application/json")
            params.put("member_id", id)

            val request = object : JsonObjectRequest(
                Request.Method.POST,
                url,
                params,
                Response.Listener<JSONObject> { response ->
                    val success = response.getBoolean("success")
//                    val message = response.getString("message")


                    if (success) {
                        Toast.makeText(this, "호출 성공", Toast.LENGTH_SHORT).show()
//                        val intent = Intent(this, Main_AfterActivity::class.java)
//                        intent.putExtra("member_id", id)
//                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "호출 실패", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    Toast.makeText(this, "서버 통신 실패", Toast.LENGTH_SHORT).show()
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Content-Type", "application/json")
                    return headers
                }
            }
            queue.add(request)
            startActivity(intent)
        }
    }


}