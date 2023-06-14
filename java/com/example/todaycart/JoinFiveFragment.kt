package com.example.todaycart

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.todaycart.R
import org.json.JSONObject


class JoinFiveFragment : Fragment() {
    private lateinit var queue: RequestQueue


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_join_five, container, false)
        val btnFive = view.findViewById<Button>(R.id.btnFive)


        queue = Volley.newRequestQueue(context)

        btnFive.setOnClickListener {
            val sf2 = requireActivity().getSharedPreferences("join", Context.MODE_PRIVATE)
            val id2 = sf2.getString("id", "error_id")
            val inputPw2 = sf2.getString("pw", "error_pw")
            val tel2 = sf2.getString("tel", "error_tel")
            val address2 = sf2.getString("address", "error_address")
            val name2 = sf2.getString("name", "error_name")
            val gender2 = sf2.getString("member_gender", "error_gender")
            val birth2 = sf2.getString("birth","error_birth")


            val url = "http://119.200.31.135:9090/project/newMember"
            val sf = requireActivity().getSharedPreferences("join", Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor = sf.edit()

            val params = JSONObject()
            params.put("Content-Type", "application/json")
            params.put("member_id",id2)
            params.put("member_pw", inputPw2)
            params.put("member_name", name2)
            params.put("member_tel", tel2)
            params.put("member_gender", gender2)
            params.put("member_add", address2)
            params.put("member_birth", birth2)


            val request = object : JsonObjectRequest(
                Request.Method.POST,
                url,
                params,
                Response.Listener<JSONObject> { response ->
                    val success = response.getBoolean("success")
                    val message = response.getString("message")

                    if (success) {
                        val intent = Intent(context, MainActivity::class.java)
                        intent.putExtra("member_id", id2)
                        intent.putExtra("member_pw", inputPw2)
                        intent.putExtra("member_name", name2)
                        intent.putExtra("member_tel", tel2)
                        intent.putExtra("member_gender", gender2)
                        intent.putExtra("member_add", address2)
                        intent.putExtra("member_birth",birth2)
                        startActivity(intent)

                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    Toast.makeText(context, "서버 통신 실패", Toast.LENGTH_SHORT).show()
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
        }

        return view
    }

}
