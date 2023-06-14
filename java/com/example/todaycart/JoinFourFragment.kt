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
import android.widget.RadioGroup
import android.widget.Toast


class JoinFourFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_join_four, container, false)
        val btnFour : Button = view.findViewById(R.id.btnFour)
        val rgGender : RadioGroup = view.findViewById(R.id.rgGender)
        val btnMale : Button = view.findViewById(R.id.btnMale)
        val btnFemale : Button = view.findViewById(R.id.btnFemale)
        val etAdd : EditText = view.findViewById(R.id.etAdd)
        val etBirth : EditText = view.findViewById(R.id.etBirth)


        // 라디오 클릭에 따른 버튼 값 감지하여 내장 DB에 저장

        rgGender.setOnCheckedChangeListener({ radioGroup, optionId ->
            val male = btnMale
            val female = btnFemale
            run {

                when (optionId) {
                    R.id.btnMale -> {
                        // db에 남자값 넣기 코드
                        val sf = requireActivity().getSharedPreferences("join",Context.MODE_PRIVATE)
                        val editor : SharedPreferences.Editor = sf.edit()
                        editor.putString("member_gender", "남")
                        editor.commit()
                    }

                    R.id.btnFemale -> {
                        // db에 여자값 넣기 코드
                        val sf = requireActivity().getSharedPreferences("join",Context.MODE_PRIVATE)
                        val editor : SharedPreferences.Editor = sf.edit()
                        editor.putString("member_gender", "여")
                        editor.commit()
                    }
                }
            }
        })

        btnFour.setOnClickListener {
            val address = etAdd.text.toString()
            val birth = etBirth.text.toString()
            if (address != ""&& birth != ""){
                val activity = activity as JoinActivity
                activity.replaceFragment(JoinFiveFragment())
                val sf = requireActivity().getSharedPreferences("join", Context.MODE_PRIVATE)
                val editor : SharedPreferences.Editor = sf.edit()

                editor.putString("address", address)
                editor.putString("birth", birth)
                editor.commit()
                // DB에서 저장된 성별 값 가져오기


            }

        }

        return view
    }


}