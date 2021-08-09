package com.dhopa.dhopaservice.cart.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.cart.adapters.AdapterChooseTopServices
import com.dhopa.dhopaservice.databinding.FragmentChooseServiceBinding
import com.dhopa.dhopaservice.mvvm.CommonViewModel
import com.dhopa.dhopaservice.network.models.Detail
import com.omninos.util_data.CommonUtils
import com.tapadoo.alerter.Alerter

class ChooseServiceFragment : Fragment(), AdapterChooseTopServices.Select {

    private lateinit var binding: FragmentChooseServiceBinding
    private lateinit var commonViewModel: CommonViewModel
    private var serviceList: MutableList<Detail> = mutableListOf()
    private var serviceId: String=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChooseServiceBinding.inflate(inflater, container, false)
        commonViewModel =
            ViewModelProviders.of(this@ChooseServiceFragment).get(CommonViewModel::class.java)

        init()
        onClick()

        return binding.root
    }

    private fun onClick() {
        binding.btnNext.setOnClickListener {
            if (serviceId==""){
                Alerter.create(activity)
                    .setTitle("Dhopa")
                    .setIcon(R.drawable.ic_warning)
                    .setText("Please choose any one service first.")
                    .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                    .show()
            }else{
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_chooseServiceFragment_to_officeHomeDetailsFragment)
            }
        }
    }
    private fun init() {
        binding.progressCircular.visibility = View.VISIBLE
        commonViewModel.serviceList(requireActivity()).observe(requireActivity(), Observer {
            if (it.success == "1") {
                serviceList = it.details as MutableList<Detail>
                Handler().postDelayed({
                    val adapter = AdapterChooseTopServices(requireActivity(), serviceList, this)
                    binding.rvTopServices.adapter = adapter

                    binding.progressCircular.visibility = View.INVISIBLE
                    binding.rvTopServices.visibility = View.VISIBLE

                }, 2000)

            } else {
                binding.progressCircular.visibility = View.INVISIBLE
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun selectItem(position: Int, id: String) {
        serviceId=id
        CommonUtils.ShowMsg(requireActivity(), "Id : $serviceId")
    }
}