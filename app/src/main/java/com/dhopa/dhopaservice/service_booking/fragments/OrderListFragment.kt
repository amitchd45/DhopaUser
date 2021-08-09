package com.dhopa.dhopaservice.service_booking.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentOrderListBinding
import com.dhopa.dhopaservice.service_booking.adapters.AdapterInProcessService
import com.dhopa.dhopaservice.service_booking.adapters.AdapterOrderList

class OrderListFragment : Fragment(), AdapterOrderList.Select {

    private lateinit var binding :FragmentOrderListBinding
    private lateinit var view1 :View
    private lateinit var adapter : AdapterOrderList


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderListBinding.inflate(inflater, container, false)
        view1=binding.root

        init()

        return view1
    }

    private fun init() {
        adapter= AdapterOrderList(requireActivity(),this)
        binding.rvOrderList.adapter=adapter
    }

    override fun onClick(position: Int) {
        Navigation.findNavController(view1).navigate(R.id.action_orderListFragment_to_myOrderFragment)
    }
}