package com.dhopa.dhopaservice.loginRegister.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dhopa.dhopaservice.databinding.FragmentAccountVerifyedBinding
import com.dhopa.dhopaservice.home.activities.HomeActivity

class AccountVerifyedFragment : Fragment() {

    private lateinit var binding: FragmentAccountVerifyedBinding
    private lateinit var view1: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountVerifyedBinding.inflate(inflater, container, false)
        view1 = binding.root

        binding.btnDone.setOnClickListener {
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        return view1
    }
}