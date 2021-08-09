package com.dhopa.dhopaservice.profile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.beaute.user.SharePrefrence.App
import com.beaute.user.SharePrefrence.AppConstants
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentMyAddressesBinding
import com.dhopa.dhopaservice.mvvm.CommonViewModel
import com.dhopa.dhopaservice.network.models.Detail
import com.dhopa.dhopaservice.network.models.Details
import com.dhopa.dhopaservice.profile.adapter.AdapterAddressList
import com.dhopa.dhopaservice.profile.models.AddressDetail
import com.dhopa.dhopaservice.profile.models.GetUserAddressModel
import com.omninos.util_data.CommonUtils

class MyAddressesFragment : Fragment(), AdapterAddressList.Select {
    private lateinit var binding : FragmentMyAddressesBinding
    private lateinit var view1 :View
    private lateinit var adapterAddress:AdapterAddressList
    private lateinit var commonViewModel: CommonViewModel
    private lateinit var userId:String
    private  var loginDetails: Details = App.appPreference1?.getUserDetails(AppConstants.USER_LOGIN_DETAILS, Details::class.java)!!
    private  var addressList: MutableList<AddressDetail> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentMyAddressesBinding.inflate(inflater, container, false)
        view1 =binding.root

        commonViewModel =
            ViewModelProviders.of(this@MyAddressesFragment).get(CommonViewModel::class.java)

        init()
        bindData()

        return view1
    }

    private fun bindData() {
        CommonUtils.showProgress(requireActivity())
        val data: HashMap<String, String> = HashMap()
        data["userId"] = loginDetails.id

        commonViewModel.getUserAddress(requireActivity(),data).observe(requireActivity(), Observer {
            if (it.success=="1"){
                CommonUtils.dismissProgress()
                addressList= it.details as MutableList<AddressDetail>
                bindListData(addressList)

            }else{
                CommonUtils.dismissProgress()
                Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun bindListData(addressList: MutableList<AddressDetail>) {
        adapterAddress= AdapterAddressList(requireActivity(),addressList,this)
        binding.rvAddress.adapter=adapterAddress
    }

    private fun init() {

        binding.btnAddAddress.setOnClickListener{
            binding.root.findNavController().navigate(R.id.action_myAddressesFragment_to_addressFieldFragment)
        }

    }

    override fun deleteAddress(position: Int, addressId: String) {
        CommonUtils.showProgress(requireActivity())
        val data: HashMap<String, String> = HashMap()
        data["addressId"] = addressId
        commonViewModel.deleteUserAddress(requireActivity(),data).observe(requireActivity(), Observer {
            if (it.success=="1"){
                CommonUtils.dismissProgress()
                Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                bindData()

            }else{
                CommonUtils.dismissProgress()
                Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
            }
        })

    }
}