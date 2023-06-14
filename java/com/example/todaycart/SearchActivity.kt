package com.example.todaycart

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class SearchActivity : AppCompatActivity() {

    // on below line we are creating variables for
    // our swipe to refresh layout, recycler view,
    // adapter and list.
    lateinit var rcvList: RecyclerView
    lateinit var searchAdapter: SearchAdapter
    lateinit var searchList: ArrayList<ProductVO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        rcvList = findViewById(R.id.rcvList)
        searchList = ArrayList()

        val btnSearching: ImageButton = findViewById(R.id.btnSearching)
        val etSearch: TextView = findViewById(R.id.etSearch)

        searchAdapter = SearchAdapter(searchList)
        rcvList.adapter = searchAdapter
        rcvList.layoutManager = LinearLayoutManager(this)
        btnSearching.setOnClickListener {
            val searchText = etSearch.text.toString()
            Log.d("받는 거", searchText)
            searchRequest(searchText, searchList)
        }
    }
    // 서버 요청 및 데이터 처리를 위한 함수를 정의합니다.
    private fun searchRequest(searchText: String , searchList: ArrayList<ProductVO>) {
        // Volley 요청 큐를 초기화합니다.
        val queue = Volley.newRequestQueue(this)

        // 서버 URL을 설정합니다.
        val url = "http://119.200.31.135:9090/project/searchProduct?p_name=$searchText" // 서버 주소와 쿼리 파라미터 설정
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                Log.d("테스트용", url)
                Log.d("테스트용", "응답 받음")

                if (response.isNull("p_name")) {
                    // 데이터가 비어 있을 경우 처리
                    Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
                } else {
                    val pName = response.getString("p_name")
                    val pPrice = response.getInt("p_price")
                    val pLoc = response.getString("p_loc")
                    val pImg = response.getString("p_img")

                    val productVO = ProductVO(p_name = pName, p_price = pPrice.toInt(), p_loc = pLoc, p_img = pImg)
                    searchList.clear()
                    searchList.add(productVO)
                    searchAdapter.notifyDataSetChanged()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
            })

        queue.add(request)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // below line is to get our inflater
        val inflater = menuInflater

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu)

        // below line is to get our menu item.
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)

        // getting search view of our item.
        val searchView: SearchView = searchItem.getActionView() as SearchView

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(msg)
                return false
            }
        })
        return true
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<ProductVO> = ArrayList()

        // running a for loop to compare elements.
        for (item in searchList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.p_name.toLowerCase().contains(text.toLowerCase())) {

                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            searchAdapter.filterList(filteredlist)
        }
    }


}
