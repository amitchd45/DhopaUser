package com.dhopa.dhopaservice.sidemenu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentInProcessBinding
import com.dhopa.dhopaservice.databinding.FragmentProgressServiceBinding
import com.dhopa.dhopaservice.service_booking.adapters.AdapterInProcessService
import com.dhopa.dhopaservice.sidemenu.adapters.AdapterInProcessTrackingService

class ProgressServiceFragment : Fragment(), AdapterInProcessTrackingService.Select {

    private lateinit var binding : FragmentProgressServiceBinding
    private lateinit var view1 : View
    private lateinit var adapter: AdapterInProcessTrackingService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProgressServiceBinding.inflate(inflater, container, false)
        view1=binding.root

        init()

        return view1
    }

    private fun init() {
        adapter= AdapterInProcessTrackingService(requireActivity(),this)
        binding.rvInProgressService.adapter=adapter
    }

    override fun click(position: Int) {
        Navigation.findNavController(view1).navigate(R.id.action_trackingServiceFragment_to_liveTrackingFragment)
    }


}