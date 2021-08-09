package com.dhopa.dhopaservice.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.navigation.Navigation
import com.dhopa.dhopaservice.R

class PlaceYourOrderFragment : Fragment() {
    private lateinit var view1:View
    private lateinit var rl_home:RelativeLayout
    private lateinit var rl_office:RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_place_your_order, container, false)

        init()
        onClick()

        return view1
    }

    private fun onClick() {
        rl_home.setOnClickListener {
            Navigation.findNavController(view1).navigate(R.id.action_placeYourOrderFragment_to_officeHomeDetailsFragment)
        }
        rl_office.setOnClickListener {
            Navigation.findNavController(view1).navigate(R.id.action_placeYourOrderFragment_to_officeHomeDetailsFragment)
        }
    }

    private fun init() {
        rl_home=view1.findViewById(R.id.rl_home)
        rl_office=view1.findViewById(R.id.rl_office)
    }
}