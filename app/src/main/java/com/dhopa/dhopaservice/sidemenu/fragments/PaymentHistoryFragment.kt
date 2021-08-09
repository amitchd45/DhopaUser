package com.dhopa.dhopaservice.sidemenu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentPaymentHistoryBinding
import com.dhopa.dhopaservice.sidemenu.adapters.AdapterPaymentHistory

class PaymentHistoryFragment : Fragment() {

    private lateinit var binding:FragmentPaymentHistoryBinding

    private lateinit var view1:View;
    private lateinit var adapter: AdapterPaymentHistory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentPaymentHistoryBinding.inflate(inflater,container,false)
        view1=binding.root

        init()
        setRecycler()

        return view1
    }

    private fun setRecycler() {
        adapter=AdapterPaymentHistory()
        binding.rvPaymentHistory.adapter=adapter
    }

    private fun init() {

    }
}