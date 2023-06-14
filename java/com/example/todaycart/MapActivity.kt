package com.example.todaycart

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.todaycart.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject


class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)




        //청과야채 버튼
        val btnVege: Button = findViewById(R.id.btnV_1)
        val bntVege2: Button = findViewById(R.id.btnV_2)
        val btnVege3: Button = findViewById(R.id.btnV_3)
        val btnVege4: Button = findViewById(R.id.btnV_4)

        //신선상품 버튼
        val btnFresh: Button = findViewById(R.id.btnF_1)

        //계란 김치 버튼
        val btnEgg: Button = findViewById(R.id.btnF_2)

        //와인,주류,위스키 버튼
        val btnDrink: Button = findViewById(R.id.btnD_1)
        val btnDrink2: Button = findViewById(R.id.btnD_2)

        //생활용품버튼
        val btnSang: Button = findViewById(R.id.btnS_1_1)
        val btnSang2: Button = findViewById(R.id.btnS_1_2)
        val btnSang3: Button = findViewById(R.id.btnS_1_3)

        //과자버튼
        val btnCook: Button = findViewById(R.id.btnC_1)
        val btnCook2: Button = findViewById(R.id.btnC_2)
        val btnCook3: Button = findViewById(R.id.btnC_3)
        val btnCook4: Button = findViewById(R.id.btnC_4)
        val btnCook5: Button = findViewById(R.id.btnC_5)

        //음료버튼
        val btnJuice: Button = findViewById(R.id.btnJ)


        // 버튼기능

        //        data class SnackItem(
//            val name: String,
//            val price: Int,
//            val location: String
//        )
        data class BeforeProduct(
            val p_name: String,
            val p_price: Int,
            val p_loc: String,
            val p_img: String // 이미지 경로를 저장하는 속성
        )

        fun showProductDialog(items: Array<BeforeProduct>) {
            class CustomListAdapter(context: Context, private val items: Array<BeforeProduct>) :
                ArrayAdapter<BeforeProduct>(context, 0, items) {
                /// 커스텀 어댑터 클래스 생성 (ArrayAdapter<String> 를 상속)
                //getView메서드 오버라이드
                // ---> 각 항목에 대한 뷰 구성
                @SuppressLint("SuspiciousIndentation")
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    var itemView = convertView
                    if (itemView == null) {
                        itemView = LayoutInflater.from(context)
                            .inflate(R.layout.product_list, parent, false)
                    }

                    // 항목에 대한 뷰 구성
                    val tvName: TextView? = itemView?.findViewById(R.id.tvName)
                    val tvPrice: TextView? = itemView?.findViewById(R.id.tvPrice)
                    val tvLoc: TextView? = itemView?.findViewById(R.id.tvLoc)
                    val imgProduct: ImageView? = itemView?.findViewById(R.id.imgProduct)


                    // 해당 항목의 BeforeProduct 가져오기
                    val productItem: BeforeProduct = items[position]

                    // 항목에 대한 정보 설정
                    tvName?.text = productItem.p_name
                    tvLoc?.text = productItem.p_loc
                    tvPrice?.text = productItem.p_price.toString()

                    // 이미지 설정
                    if (productItem.p_img.isNotEmpty()) {
                        // 이미지 경로가 비어있지 않은 경우
                        Glide.with(context)
                            .load("http://119.200.31.135:9090/project/productUpload"+productItem.p_img)
                            .placeholder(R.drawable.noimg) // 이미지 로드 중에 표시할 임시 이미지
                            .error(R.drawable.noimg) // 이미지 로드 실패 시 표시할 이미지
                            .into(imgProduct!!)
                    }

                    return itemView!!
                }
            }

            // CustomListAdapter 객체를 생성
            // 생성자에는 context와 items 배열을 전달
            val adapter = CustomListAdapter(this, items)

            //AlertDialog.Builder를 생성
            AlertDialog.Builder(this).apply {
                setTitle("진열상품")

                //setAdapter메서드로 customadapter설정 --> 다이얼로그에 항목들이 표시
                // 두 번째 매개변수로는 항목을 클릭했을 때 실행될 동작을 정의하는 리스너
                setAdapter(adapter) { dialog, which ->
                    // 아이템 클릭 시 동작할 내용 작성
                    Log.d("MainActivity", "Selected item: ${items[which]}")
                    dialog.dismiss()
                }
                //"닫기" 버튼을 추가
                setPositiveButton("닫기", null)
                show()
            }
        }



        fun fetchProductData(pLoc: String) {
            val url = "http://119.200.31.135:9090/project/map?p_loc=$pLoc"

            val request = JsonArrayRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    val productList = mutableListOf<BeforeProduct>()

                    for (i in 0 until response.length()) {
                        val item = response.getJSONObject(i)
                        val name = item.getString("p_name")
                        val price = item.getInt("p_price")
                        val location = item.getString("p_loc")
                        val imagePath = item.getString("p_img") // 서버에서 이미지 경로 정보를 받아옴

                        val product = BeforeProduct(name, price, location,imagePath)
                        productList.add(product)
                    }

                    // Display the retrieved product data using the custom adapter
                    showProductDialog(productList.toTypedArray())
                },
                Response.ErrorListener { error ->
                    // Handle error
                    Log.d("imgload","error")
                }
            )

            Volley.newRequestQueue(this).add(request)
        }

        //과자버튼기능
        btnCook.setOnClickListener {
            fetchProductData("과자1-1")
        }
        btnCook2.setOnClickListener {
            fetchProductData("과자1-1")
        }
        btnCook3.setOnClickListener {
            fetchProductData("과자1-1")
        }
        btnCook4.setOnClickListener {
            fetchProductData("과자1-1")
        }
        btnCook5.setOnClickListener {
            fetchProductData("과자1-1")
        }

        //음료버튼기능
        btnJuice.setOnClickListener {
            fetchProductData("음료")
        }

        //생활용품버튼기능
        btnSang.setOnClickListener {
            fetchProductData("생활용품")
        }
        btnSang2.setOnClickListener {
            fetchProductData("생활용품")
        }
        btnSang3.setOnClickListener {
            fetchProductData("생활용품")
        }

        //와인,주류, 위스키버튼
        btnDrink.setOnClickListener {
            fetchProductData("주류")
        }
        btnDrink2.setOnClickListener {
            fetchProductData("주류")
        }

        //네비게이션바
        val bnv : BottomNavigationView = findViewById(R.id.bnv)

        bnv.setSelectedItemId(R.id.tab4)



        bnv.setOnItemSelectedListener {
            // it ---> 내가 클릭한 item의 아이디, 속성 .. 정보를 받아온다.
            // it.itemId : 내가 클릭한 항목의 id
            when(it.itemId) {
                R.id.tab1 -> {
                    val intent = Intent(this@MapActivity, ShoppingActivity::class.java)
                    startActivity(intent)

                }

                R.id.tab2 -> {
                    val intent = Intent(this@MapActivity, CallActivity::class.java)
                    startActivity(intent)
                }

                R.id.tab3 -> {
                    val intent = Intent(this@MapActivity, Main_AfterActivity::class.java)
                    startActivity(intent)
                }

                R.id.tab4 -> {
                    val intent = Intent(this@MapActivity, MapActivity::class.java)
                    startActivity(intent)
                }

                R.id.tab5 -> {
                    val intent = Intent(this@MapActivity, SearchActivity::class.java)
                    startActivity(intent)
                }


            }

            // click이벤트가 끝나지 않았다! --ㄹ민ㄷ
            // true : 클릭 이벤트가 끝났다! 다음 클릭으로 넘어가라
            true //return
        }













    }


}


