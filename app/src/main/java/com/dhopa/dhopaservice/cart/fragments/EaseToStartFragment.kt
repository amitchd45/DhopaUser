package com.dhopa.dhopaservice.cart.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentEaseToStartBinding

class EaseToStartFragment : Fragment() {
    private lateinit var binding : FragmentEaseToStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEaseToStartBinding.inflate(inflater, container, false)

        init()

        return binding.root
    }

    private fun init() {

        binding.btnSchedule.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_easeToStartFragment_to_chooseServiceFragment)
        }
    }
}