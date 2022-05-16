package com.atitienei_daniel.hampterchat.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.atitienei_daniel.hampterchat.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
    }
}