package com.sohyun.jsonplaceholder.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sohyun.jsonplaceholder.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_framelayout, MainFragment.newInstance())
                .commitNow()
        }
    }
}