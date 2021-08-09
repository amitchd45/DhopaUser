package com.dhopa.dhopaservice.profile.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dhopa.dhopaservice.databinding.FragmentReferAndEarnBinding

class ReferAndEarnFragment : Fragment() {

    private lateinit var binding: FragmentReferAndEarnBinding
    private lateinit var view1: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReferAndEarnBinding.inflate(inflater, container, false)

        view1 = binding.root

        init()

        return view1

    }

    private fun init() {
        binding.ivShare.setOnClickListener {
            Toast.makeText(activity,"coming soon...",Toast.LENGTH_SHORT).show()
        }
    }
}