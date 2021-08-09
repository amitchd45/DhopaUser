package com.dhopa.dhopaservice.profile.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.ActivityHomeBinding
import com.dhopa.dhopaservice.databinding.ActivityNotificationBinding
import com.dhopa.dhopaservice.home.adapter.AdapterTopLaundryServices
import com.dhopa.dhopaservice.profile.adapter.AdapterNotifications

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private lateinit var adapter: AdapterNotifications
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        onClick()
    }

    private fun onClick() {
        binding.tvTitle.text="Notifications"
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun init() {
        adapter = AdapterNotifications()
        binding.rvNotificationList.adapter = adapter
    }
}