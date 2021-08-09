package com.dhopa.dhopaservice.service_booking.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentBookingServicesBinding
import com.dhopa.dhopaservice.service_booking.adapters.AdapterViewPagerBookingService
import com.google.android.material.tabs.TabLayout

class BookingServicesFragment : Fragment() {

    private lateinit var binding : FragmentBookingServicesBinding
    private lateinit var view1: View
    private  var tabLayout: TabLayout?=null
    private  var viewPager: ViewPager?=null
    private lateinit var adapterViewPager: AdapterViewPagerBookingService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookingServicesBinding.inflate(inflater, container, false)
        view1=binding.root

        init()

        return view1
    }

    private fun init() {
        binding.tabLayout!!.addTab(binding.tabLayout!!.newTab().setText("In Progress"))
        binding.tabLayout!!.addTab(binding.tabLayout!!.newTab().setText("Completed"))
        binding.tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        adapterViewPager = AdapterViewPagerBookingService(requireActivity(), childFragmentManager, binding.tabLayout!!.tabCount)
        binding.viewPager!!.adapter = adapterViewPager

        binding.viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))

        binding.tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }
}