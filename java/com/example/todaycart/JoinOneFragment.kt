package com.example.todaycart

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class JoinOneFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_join_one, container, false)

        val etInputId = view.findViewById<EditText>(R.id.etInputId)
        val btnOne = view.findViewById<Button>(R.id.btnOne)





        btnOne.setOnClickListener {
            val id = etInputId.text.toString()
            if(id != ""){
                val activity = activity as JoinActivity
                activity.replaceFragment(JoinTwoFragment())

                // sf에 넣는과정
                val sf = requireActivity().getSharedPreferences("join", Context.MODE_PRIVATE)
                val editor : SharedPreferences.Editor = sf.edit()
                // editor에 etUrl에 있는 값을 저장 : Key/value
                editor.putString("id", id)
                editor.commit()

                // 꺼내는 과정

                val sf2 = requireActivity().getSharedPreferences("join", Context.MODE_PRIVATE)
                val id2 = sf2.getString("id", "error_id")


            }else if (id == ""){
                Toast.makeText(context,"아이디를 입력하세요",Toast.LENGTH_SHORT).show()
            }
        }
        // end
        return view
    }
}