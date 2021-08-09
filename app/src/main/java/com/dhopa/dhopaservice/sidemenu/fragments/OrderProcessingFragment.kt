package com.dhopa.dhopaservice.sidemenu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentOrderProcessingBinding
import com.dhopa.dhopaservice.databinding.FragmentPaymentHistoryBinding

class OrderProcessingFragment : Fragment() {

    private lateinit var binding: FragmentOrderProcessingBinding

    private lateinit var view1:View;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentOrderProcessingBinding.inflate(inflater,container,false)
        view1=binding.root

        init()

        return view1
    }

    private fun init() {
        binding.btnOrderDetails.setOnClickListener {
            Toast.makeText(activity,"coming soon...",Toast.LENGTH_SHORT).show()
        }
    }
}