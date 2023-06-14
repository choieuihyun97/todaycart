package com.example.todaycart

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.todaycart.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MapActivity2 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map2)

        //과자버튼
        val btnCook : Button = findViewById(R.id.btnC_1)
        val btnCook2 : Button = findViewById(R.id.btnC_2)
        val btnCook3 : Button = findViewById(R.id.btnC_3)
        val btnCook4 : Button = findViewById(R.id.btnC_4)
        val btnCook5 : Button = findViewById(R.id.btnC_5)

        //생활용품버튼
        val btnSang : Button = findViewById(R.id.btnS_1_1)
        val btnSang2 : Button = findViewById(R.id.btnS_1_2)
        val btnSang3 : Button = findViewById(R.id.btnS_1_3)

        //와인,주류,위스키 버튼
        val btnDrink : Button = findViewById(R.id.btnD_1)
        val btnDrink2 : Button = findViewById(R.id.btnD_2)

        //음료버튼
        val btnJuice : Button =  findViewById(R.id.btnJ)

        //위치정보가져오기
        val location = intent.getStringExtra("location")

        //버튼색상변경

        when (location) {
            "과자1-1" -> btnCook3.setBackgroundColor(Color.rgb(180, 156, 246))
            "음료" -> btnJuice.setBackgroundColor(Color.rgb(255,162,239))
            "주류" -> btnDrink.setBackgroundColor(Color.rgb(255,162,239))
            "생활용품" -> btnSang.setBackgroundColor(Color.rgb(255,162,239))
        }



        //네비게이션바
        val bnv : BottomNavigationView = findViewById(R.id.bnv)

        bnv.setSelectedItemId(R.id.tab4)



        bnv.setOnItemSelectedListener {
            // it ---> 내가 클릭한 item의 아이디, 속성 .. 정보를 받아온다.
            // it.itemId : 내가 클릭한 항목의 id
            when(it.itemId) {
                R.id.tab1 -> {
                    val intent = Intent(this@MapActivity2, ShoppingActivity::class.java)
                    startActivity(intent)

                }

                R.id.tab2 -> {
                    val intent = Intent(this@MapActivity2, CallActivity::class.java)
                    startActivity(intent)
                }

                R.id.tab3 -> {
                    val intent = Intent(this@MapActivity2, Main_AfterActivity::class.java)
                    startActivity(intent)
                }

                R.id.tab4 -> {
                    val intent = Intent(this@MapActivity2, MapActivity::class.java)
                    startActivity(intent)
                }

                R.id.tab5 -> {
                    val intent = Intent(this@MapActivity2, SearchActivity::class.java)
                    startActivity(intent)
                }


            }

            // click이벤트가 끝나지 않았다! --ㄹ민ㄷ
            // true : 클릭 이벤트가 끝났다! 다음 클릭으로 넘어가라
            true //return
        }


    }
}
