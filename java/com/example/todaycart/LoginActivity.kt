package com.example.todaycart

import android.content.Context
import android.content.ContextParams
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var queue: RequestQueue
    private lateinit var etId: EditText
    private lateinit var etPw: EditText
    private lateinit var btnDoLogin: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etId = findViewById(R.id.etId)
        etPw = findViewById(R.id.etPw)
        btnDoLogin = findViewById(R.id.btnDoLogin)

        queue = Volley.newRequestQueue(this)

        btnDoLogin.setOnClickListener {
            val sf = getSharedPreferences("login", Context.MODE_PRIVATE)
            val cartNumber = "cart01"
            val editor : SharedPreferences.Editor = sf.edit()
            val url = "http://119.200.31.135:9090/project/loginCheckMember?cart_id=${cartNumber.toString()}"
            Log.d("cartNumber",cartNumber.toString())
            val id = etId.text.toString()
            val pw = etPw.text.toString()

            if (id.isNotEmpty() && pw.isNotEmpty()) {
                val params = JSONObject()
                params.put("Content-Type", "application/json")
                params.put("member_id", id)
                params.put("member_pw", pw)

                val request = object : JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    params,
                    Response.Listener<JSONObject> { response ->
                        val success = response.getBoolean("success")
                        val message = response.getString("message")

                        if (success) {
                            val intent = Intent(this@LoginActivity, Main_AfterActivity::class.java)
                            editor.putString("member_id",id)
                            editor.putString("member_pw",pw)
                            editor.commit()
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        }
                    },
                    Response.ErrorListener { error ->
                        error.printStackTrace()
                        Toast.makeText(this, "서버 통신 실패", Toast.LENGTH_SHORT).show()
                    }


                )

                {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers.put("Content-Type", "application/json")
                        return headers
                    }
                }


                queue.add(request)

            } else {
                Toast.makeText(this, "아이디/비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onApplyThemeResource(theme: Resources.Theme?, resid: Int, first: Boolean) {
        super.onApplyThemeResource(theme, resid, first)
    }

}