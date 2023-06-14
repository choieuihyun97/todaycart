package com.example.todaycart

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class JoinTwoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_join_two, container, false)

        val etInputPw = view.findViewById<EditText>(R.id.etInputPw)
        val etCheckPw = view.findViewById<EditText>(R.id.etCheckPw)
        val btnTwo = view.findViewById<Button>(R.id.btnTwo)

        btnTwo.setOnClickListener {
            val inputPw = etInputPw.text.toString()
            val checkPw = etCheckPw.text.toString()
            if (etInputPw.length() < 6) {
                etInputPw.setError("비밀번호는 최소 6자리 이상이어야 합니다.");
            }else if((inputPw != "" || checkPw != "")&&(inputPw==checkPw)){

                val activity = activity as JoinActivity
                activity.replaceFragment(JoinThreeFragment())

                // sf에 넣는과정
                val sf = requireActivity().getSharedPreferences("join", Context.MODE_PRIVATE)
                val editor : SharedPreferences.Editor = sf.edit()
                // editor에 etUrl에 있는 값을 저장 : Key/value
                editor.putString("pw", inputPw)
                editor.commit()


            }
            if(inputPw =="" || checkPw == ""){
                Toast.makeText(context,"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show()
            }else if (inputPw != checkPw){
                Toast.makeText(context, "비밀번호가 일치하지 않습니다",Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }



}