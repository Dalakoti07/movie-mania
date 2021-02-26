package com.dalakoti07.android.moviemania.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dalakoti07.android.moviemania.R

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}