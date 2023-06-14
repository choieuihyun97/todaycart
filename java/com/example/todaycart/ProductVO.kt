package com.example.todaycart

import android.graphics.Bitmap

data class ProductVO(
    var p_code: Int = 0,
    var p_name: String = "",
    var p_price: Int= 0,
    var p_loc: String = "",
    var p_weight: Int = 0,
    var p_img: String = ""
)