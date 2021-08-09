package com.dhopa.dhopaservice.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.navigation.findNavController
import com.dhopa.dhopaservice.R

class PostCodeFragment : Fragment() {

    private lateinit var view1:View
    private lateinit var btnGO:RelativeLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_post_code, container, false)

        init()
        onClick()


        return view1
    }

    private fun onClick() {
        btnGO.setOnClickListener {
            view1.findNavController().navigate(R.id.action_postCodeFragment_to_placeYourOrderFragment)
        }
    }

    private fun init() {
        btnGO=view1.findViewById(R.id.rl_go)

    }
}