package com.dhopa.dhopaservice.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.beaute.user.SharePrefrence.App
import com.beaute.user.SharePrefrence.AppConstants
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentSchedulePickUpBinding
import com.dhopa.dhopaservice.mvvm.CommonViewModel
import com.dhopa.dhopaservice.network.models.Details
import com.dhopa.dhopaservice.profile.adapter.AdapterAddressList
import com.dhopa.dhopaservice.profile.adapter.AdapterAddressListDlivery
import com.dhopa.dhopaservice.profile.models.AddressDetail
import com.omninos.util_data.CommonUtils
import com.tapadoo.alerter.Alerter

class SchedulePickUpFragment : Fragment(), AdapterAddressList.Select,
    AdapterAddressListDlivery.Select {

    private lateinit var binding: FragmentSchedulePickUpBinding
    private lateinit var view1: View
    private var loginDetails: Details =
        App.appPreference1?.getUserDetails(AppConstants.USER_LOGIN_DETAILS, Details::class.java)!!
    private lateinit var commonViewModel: CommonViewModel
    private var addressList: MutableList<AddressDetail> = mutableListOf()
    private lateinit var adapterAddress: AdapterAddressListDlivery
    private var deliveryAddressId:String=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSchedulePickUpBinding.inflate(inflater, container, false)

        view1 = binding.root
        commonViewModel = ViewModelProviders.of(this@SchedulePickUpFragment).get(CommonViewModel::class.java)
        init()
        bindData()

        return view1
    }

    private fun bindData() {
        CommonUtils.showProgress(requireActivity())
        val data: HashMap<String, String> = HashMap()
        data["userId"] = loginDetails.id

        commonViewModel.getUserAddress(requireActivity(), data)
            .observe(requireActivity(), Observer {
                if (it.success == "1") {
                    CommonUtils.dismissProgress()
                    addressList = it.details as MutableList<AddressDetail>
                    bindListData(addressList)
                } else {
                    CommonUtils.dismissProgress()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun bindListData(addressList: MutableList<AddressDetail>) {
        adapterAddress = AdapterAddressListDlivery(requireActivity(), addressList, this)
        binding.rvAddress.adapter = adapterAddress
    }

    private fun init() {
        binding.LLAddAddress.setOnClickListener {
            Navigation.findNavController(view1)
                .navigate(R.id.action_schedulePickUpFragment_to_addressFieldFragment)
        }
        binding.btnContinue.setOnClickListener {
            if (deliveryAddressId==""){
                Alerter.create(activity)
                    .setTitle("Dhopa")
                    .setIcon(R.drawable.ic_warning)
                    .setText("Select delivery address first")
                    .setBackgroundColorRes(R.color.colorAccent)
                    .show()
            }else{
                Navigation.findNavController(view1)
                    .navigate(R.id.action_schedulePickUpFragment_to_schedulePickUpDateTimeFragment)
            }
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

    override fun viewAddress(addressDetails: AddressDetail) {
        CommonUtils.ShowMsg(activity,addressDetails.address1+" "+addressDetails.address2+" "+addressDetails.country+" "+addressDetails.city)
    }

    override fun selectDeliverAddress(addressId: String) {
        deliveryAddressId=addressId
    }
}