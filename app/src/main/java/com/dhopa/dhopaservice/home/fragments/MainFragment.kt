package com.dhopa.dhopaservice.home.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentMainBinding
import com.dhopa.dhopaservice.home.ModelClass.Services
import com.dhopa.dhopaservice.home.adapter.AdapterTopLaundryServices
import com.dhopa.dhopaservice.home.adapter.AdapterTopServices
import com.dhopa.dhopaservice.mvvm.CommonViewModel
import com.dhopa.dhopaservice.network.models.Detail
import com.dhopa.dhopaservice.network.models.ServiceListModel
import com.dhopa.dhopaservice.profile.fragment.ProfileFragment
import com.omninos.util_data.CommonUtils

class MainFragment: Fragment(),ProfileFragment.Select{

    private lateinit var binding: FragmentMainBinding
    private lateinit var view1:View
    private lateinit var commonViewModel: CommonViewModel
    private  var serviceList: MutableList<Detail> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)

        commonViewModel =
            ViewModelProviders.of(this@MainFragment).get(CommonViewModel::class.java)
        view1=binding.root

        init()

        onClick()

        return view1
    }

    private fun onClick() {
        binding.rlGo.setOnClickListener { orderToday() }
    }

    private fun orderToday() {
        Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_easeToStartFragment)
    }

    private fun init() {

        CommonUtils.showProgress(requireActivity())
        commonViewModel.serviceList(requireActivity()).observe(requireActivity(), Observer {
            if (it.success=="1"){
                CommonUtils.dismissProgress()
                serviceList= it.details as MutableList<Detail>

                val adapter = AdapterTopServices(requireActivity(),serviceList)
                binding.rvTopServices.adapter = adapter

                val adapter1 = AdapterTopLaundryServices()
                binding.rvTopPreview.adapter = adapter1

            }else{
                CommonUtils.dismissProgress()
                Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun sendDetails(image: String, name: String) {

        Log.i("data", "sendDetails: ===$image\n$name")
//        Glide.with(requireActivity()).load(it.details.image).placeholder(R.drawable.userimage).into(binding.ivImage)
//        binding.tvName.text=it.details.name+" "+it.details.lastName
    }
}