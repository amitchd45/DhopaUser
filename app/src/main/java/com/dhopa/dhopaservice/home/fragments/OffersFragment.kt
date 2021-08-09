package com.dhopa.dhopaservice.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentOffersBinding
import com.dhopa.dhopaservice.home.adapter.AdapterOffers
import com.dhopa.dhopaservice.service_booking.adapters.AdapterInProcessService
import com.dhopa.dhopaservice.service_booking.adapters.AdapterOrderList

class OffersFragment : Fragment() {

    private lateinit var binding: FragmentOffersBinding
    private lateinit var view1 :View
    private lateinit var adapter : AdapterOffers

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOffersBinding.inflate(inflater, container, false)
        view1 =binding.root

        init()

        return view1
    }

    private fun init() {
        adapter= AdapterOffers()
        binding.rvOffers.adapter=adapter
    }
}