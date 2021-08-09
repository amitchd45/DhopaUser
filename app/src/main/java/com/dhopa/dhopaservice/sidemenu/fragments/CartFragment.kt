package com.dhopa.dhopaservice.sidemenu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentCartBinding
import com.dhopa.dhopaservice.home.activities.HomeActivity.Companion.bottomNavView

class CartFragment : Fragment() {

    private lateinit var binding:FragmentCartBinding
    private lateinit var view1:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentCartBinding.inflate(inflater,container,false)
        view1=binding.root

        init()

        return view1
    }

    private fun init() {

    }
}