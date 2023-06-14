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


class JoinThreeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_join_three, container, false)
        val etTel : EditText = view.findViewById(R.id.etTel)
        val etName: EditText = view.findViewById(R.id.etName)
        val btnThree : Button = view.findViewById(R.id.btnThree)

        btnThree.setOnClickListener {
            val tel = etTel.text.toString()
            val name = etName.text.toString()
            if (tel != ""&&name!=""){
                val activity = activity as JoinActivity
                activity.replaceFragment(JoinFourFragment())
                val sf = requireActivity().getSharedPreferences("join", Context.MODE_PRIVATE)
                val editor : SharedPreferences.Editor = sf.edit()
                // editor에 etUrl에 있는 값을 저장 : Key/value
                editor.putString("tel", tel)
                editor.putString("name", name)
                editor.commit()

            }
            if (name == ""){
                Toast.makeText(context,"이름을 입력하세요",Toast.LENGTH_SHORT).show()
            }else if (tel == ""){
                Toast.makeText(context,"휴대전화 번호를 입력하세요",Toast.LENGTH_SHORT).show()
            }

        }



        return view
    }


}