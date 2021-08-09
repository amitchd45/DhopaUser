package com.dhopa.dhopaservice.home.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.beaute.user.SharePrefrence.AppConstants
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentPaymentBinding
import com.dhopa.dhopaservice.mvvm.CommonViewModel
import com.omninos.util_data.CommonUtils

class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding
    private lateinit var view1: View
    private lateinit var commonViewModel: CommonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        commonViewModel =
            ViewModelProviders.of(this@PaymentFragment).get(CommonViewModel::class.java)
        view1 = binding.root
        init()

        return view1
    }

    private fun init() {
        binding.btnContinuePay.setOnClickListener {
            validation()
        }
    }

    private fun validation() {
        CommonUtils.showProgress(requireActivity())
        val data: HashMap<String, String> = HashMap()
        data["userId"] = "1"
        data["serviceIds"] = "5"
        data["locationId"] = "2"
        data["pickupAddress"] = "Mohali Tdi city"
        data["pickupAddressLat"] = "0.0"
        data["pickupAddressLong"] = "0.0"
        data["name"] = "Amit"
        data["email"] = "dhopa@gmail.com"
        data["phone"] = "9876543210"
        data["pickupAddressType"] = "home"
        data["dropAddressId"] = "2"
        data["pickupDate"] = "2021-04-21"
        data["pickupStartTime"] = "10:20:30"
        data["pickupEndTime"] = "11:20:30"
        data["dropDate"] = "2021-04-29"
        data["dropStartTime"] = "13:20:30"
        data["dropEndTime"] = "14:20:30"
        data["subTotalAmount"] = "100"
        data["tax"] = "4"
        data["totalAmount"] = "104"

        commonViewModel.bookingOrder(requireActivity(), data).observe(requireActivity(), Observer {
            if (it.success == "1") {
                CommonUtils.ShowMsg(requireActivity(),it.message)
                Handler().postDelayed({
                    CommonUtils.dismissProgress()
                    CommonUtils.ShowMsg(requireActivity(),it.message)
                    val bundle=Bundle()
                    bundle.putString(AppConstants.ORDER_ID,it.details.orderId)
                    bundle.putString(AppConstants.SUB_TOTAL,it.details.subTotalAmount)
                    bundle.putString(AppConstants.FINAL_AMT,it.details.totalAmount)
                    view1.findNavController()
                        .navigate(R.id.action_paymentFragment_to_puckupConformationFragment)
                }, 2000)
            } else {
                CommonUtils.dismissProgress()
                CommonUtils.ShowMsg(requireActivity(),it.message)

            }
        })
    }
}