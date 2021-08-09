package com.dhopa.dhopaservice.service_booking.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dhopa.dhopaservice.databinding.FragmentCompletedServiceBinding
import com.dhopa.dhopaservice.service_booking.adapters.AdapterInCompletedService

class CompletedServiceFragment : Fragment() {
    private lateinit var binding: FragmentCompletedServiceBinding

    private lateinit var view1: View
    private lateinit var adapter: AdapterInCompletedService
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompletedServiceBinding.inflate(inflater, container, false)
        view1 = binding.root

        init()

        return view1
    }

    private fun init() {
        adapter = AdapterInCompletedService()
        binding.rvCompletedService.adapter = adapter
    }
}