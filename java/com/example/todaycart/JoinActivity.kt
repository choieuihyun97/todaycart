package com.example.todaycart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

class JoinActivity : AppCompatActivity() {
    private var index = 0
    private val fragmentList = listOf(JoinOneFragment(), JoinTwoFragment(), JoinThreeFragment(), JoinFourFragment(), JoinFiveFragment())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        val joinFl: FrameLayout = findViewById(R.id.joinFl)

        replaceFragment(JoinOneFragment())
    }
    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.joinFl, fragment)
            .commit()
    }
}