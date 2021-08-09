package com.dhopa.dhopaservice.loginRegister.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beaute.user.SharePrefrence.AppConstants
import com.dhopa.dhopaservice.databinding.FragmentCongratulationBinding

class CongratulationFragment : Fragment() {

    private lateinit var binding: FragmentCongratulationBinding
    private lateinit var view1:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCongratulationBinding.inflate(inflater, container, false)
        view1=binding.root



        return view1
    }

}