package com.dhopa.dhopaservice.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.ActivityMyCartBinding

class MyCartActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMyCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }

    private fun init() {
        binding.ivBack.setOnClickListener { back() }
    }

    private fun back() {
        onBackPressed()
    }
}