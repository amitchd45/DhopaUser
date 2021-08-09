package com.dhopa.dhopaservice.loginRegister.fragments

import `in`.aabhasjindal.otptextview.OTPListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.beaute.user.SharePrefrence.AppConstants
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentOtpVerificationBinding
import com.dhopa.dhopaservice.mvvm.CommonViewModel
import com.dhopa.dhopaservice.utils.Util
import com.omninos.util_data.CommonUtils
import com.tapadoo.alerter.Alerter

class OtpVerificationFragment : Fragment() {
    private lateinit var binding: FragmentOtpVerificationBinding
    private lateinit var commonViewModel: CommonViewModel
    private lateinit var view1: View
    private lateinit var strName: String
    private lateinit var strEmail: String
    private lateinit var strPhone: String
    private lateinit var strPassword: String
    private lateinit var backOtp: String
    private lateinit var newOtp: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtpVerificationBinding.inflate(inflater, container, false)
        view1 = binding.root

        commonViewModel =
            ViewModelProviders.of(this@OtpVerificationFragment).get(CommonViewModel::class.java)

        init()
        storeBackDetails()
        otpView()


        return view1
    }

    private fun storeBackDetails() {
        strName= arguments?.getString(AppConstants.USER_NAME).toString()
        strEmail= arguments?.getString(AppConstants.USER_EMAIL).toString()
        strPhone= arguments?.getString(AppConstants.USER_PHONE).toString()
        strPassword= arguments?.getString(AppConstants.USER_PASSWORD).toString()
        backOtp= arguments?.getString(AppConstants.USER_OTP).toString()
    }

    private fun otpView() {
        binding.otpView?.otpListener = object : OTPListener {
            override fun onInteractionListener() {


            }

            override fun onOTPComplete(otp: String) {
                if (backOtp == otp) {
                    newOtp = otp
                    Util.closeKeyBoard(requireActivity())
                }
            }
        }
    }

    private fun init() {
        binding.btnSubmit.setOnClickListener {
            if (backOtp == newOtp) {
                register()
            } else {
                Alerter.create(activity)
                    .setTitle("Dhopa")
                    .setIcon(R.drawable.ic_warning)
                    .setText("otp mismatch...")
                    .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                    .show()
            }
        }

        binding.tvResend.setOnClickListener {
           resendOtp()
        }
    }

    private fun register() {
        CommonUtils.showProgress(requireActivity())
        val data: HashMap<String, String> = HashMap()
        data["name"] = strName
        data["email"] = strEmail
        data["phone"] = strPhone
        data["password"] = strPassword
        data["refralId"] = ""
        data["reg_id"] = "123"
        data["device_type"] = "Android"
        data["latitude"] = "30.647478"
        data["longitude"] = "70.798589"

        commonViewModel.register(requireActivity(), data).observe(requireActivity(), Observer {
            if (it.success == "1") {
                CommonUtils.dismissProgress()
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                view1.findNavController()
                    .navigate(R.id.action_otpVerificationFragment_to_accountVerifyedFragment)


            } else {
                CommonUtils.dismissProgress()
                Alerter.create(activity)
                    .setTitle("Dhopa")
                    .setIcon(R.drawable.ic_warning)
                    .setText(it.message)
                    .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                    .show()
            }
        })
    }

    private fun resendOtp() {
        CommonUtils.showProgress(requireActivity())
        val data: HashMap<String, String> = HashMap()
        data["email"] = strEmail
        data["phone"] = strPhone

        commonViewModel.checkPhoneEmail(requireActivity(), data)
            .observe(requireActivity(), Observer {
                if (it.success == "1") {
                    CommonUtils.dismissProgress()
                    Alerter.create(activity)
                        .setTitle("Dhopa")
                        .setIcon(R.drawable.ic_warning)
                        .setText("New OTP : "+it.otp)
                        .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                        .show()
                    backOtp=it.otp

                } else {
                    CommonUtils.dismissProgress()
                    Alerter.create(activity)
                        .setTitle("Dhopa")
                        .setIcon(R.drawable.ic_warning)
                        .setText(it.message)
                        .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                        .show()
                }
            })
    }
}