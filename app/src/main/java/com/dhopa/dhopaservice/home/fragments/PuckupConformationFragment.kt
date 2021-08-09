package com.dhopa.dhopaservice.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.beaute.user.SharePrefrence.AppConstants
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentPuckupConformationBinding

class PuckupConformationFragment : Fragment() {
    private lateinit var binding : FragmentPuckupConformationBinding
    private lateinit var orderId:String
    private lateinit var subTotal:String
    private lateinit var finalAmount:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPuckupConformationBinding.inflate(inflater, container, false)

        orderId=arguments?.getString(AppConstants.ORDER_ID).toString()
        subTotal=arguments?.getString(AppConstants.SUB_TOTAL).toString()
        finalAmount=arguments?.getString(AppConstants.FINAL_AMT).toString()

        init()
        setData()

        return binding.root
    }

    private fun setData() {
        binding.tvOrderId.text=orderId
        binding.tvInitialAmt.text=subTotal
        binding.tvFinalAmt.text=finalAmount
    }

    private fun init() {
        binding.btnDone.setOnClickListener {
            Toast.makeText(requireContext(),"Coming soon...",Toast.LENGTH_SHORT).show()
        }
    }
}