package com.dhopa.dhopaservice.recover_password

import `in`.aabhasjindal.otptextview.OTPListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.beaute.user.SharePrefrence.AppConstants
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentForgotPasswordBinding
import com.dhopa.dhopaservice.mvvm.CommonViewModel
import com.dhopa.dhopaservice.utils.Util
import com.omninos.util_data.CommonUtils
import com.tapadoo.alerter.Alerter
import java.util.regex.Matcher
import java.util.regex.Pattern

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var commonViewModel: CommonViewModel
    private lateinit var strEmail: String
    private lateinit var strPassword: String 

    private  var backOtp: String="0"
    private  var newOtp: String="1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)

        commonViewModel =
            ViewModelProviders.of(this@ForgotPasswordFragment).get(CommonViewModel::class.java)

        init()
        otpView()

        return binding.root
    }

    private fun init() {

        binding.btnNext.setOnClickListener {
            if (backOtp == newOtp) {
                var data =Bundle()
                data.putString(AppConstants.USER_EMAIL,strEmail)
                binding.root.findNavController().navigate(R.id.action_forgotPasswordFragment_to_resetPasswordFragment,data)
            } else {
                validation()
            }
        }
        binding.tvResend.setOnClickListener { resend() }

    }

    private fun validation() {
        strEmail = binding.etEmail.text.toString().trim()
        if (strEmail.isEmpty()) {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please enter email")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        } else if (!emailValidator(strEmail)) {

            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please enter valid email")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()

        } else {
            resetPass()
        }
    }

    private fun resetPass() {
        CommonUtils.showProgress(requireActivity())
        val data: HashMap<String, String> = HashMap()
        data["email"] = strEmail

        commonViewModel.forgotPassword(requireActivity(), data)
            .observe(requireActivity(), Observer {
                if (it.success == "1") {
                    CommonUtils.dismissProgress()
                    binding.llOtp.visibility = View.VISIBLE
                    backOtp = it.otp
                    Alerter.create(activity)
                        .setTitle("Dhopa")
                        .setIcon(R.drawable.ic_warning)
                        .setText("OTP : "+it.otp)
                        .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                        .show()
                    binding.btnNext.text = "Verify"
                } else {
                    CommonUtils.dismissProgress()
                    Alerter.create(activity)
                        .setTitle("Dhopa")
                        .setIcon(R.drawable.ic_warning)
                        .setText(it.message)
                        .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                        .show()
                    binding.llOtp.visibility = View.GONE
                }
            })
    }

    private fun resend() {
        CommonUtils.showProgress(requireActivity())
        val data: HashMap<String, String> = HashMap()
        data["email"] = strEmail

        commonViewModel.forgotPassword(requireActivity(), data)
            .observe(requireActivity(), Observer {
                if (it.success == "1") {
                    CommonUtils.dismissProgress()
                    backOtp = it.otp
                    Alerter.create(activity)
                        .setTitle("Dhopa")
                        .setIcon(R.drawable.ic_warning)
                        .setText("New OTP : "+it.otp)
                        .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                        .show()
                }else{
                    Alerter.create(activity)
                        .setTitle("Dhopa")
                        .setIcon(R.drawable.ic_warning)
                        .setText(it.message)
                        .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                        .show()
                }
            })
    }

    private fun otpView() {
        binding.otpView?.otpListener = object : OTPListener {
            override fun onInteractionListener() {


            }

            override fun onOTPComplete(otp: String) {
                if (backOtp == otp) {
                    newOtp = otp
                    Util.closeKeyBoard(requireActivity())
                }else{
                    Alerter.create(activity)
                        .setTitle("Dhopa")
                        .setIcon(R.drawable.ic_warning)
                        .setText("OTP Mismatch...")
                        .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                        .show()
                }
            }
        }
    }

    fun emailValidator(email: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(EMAIL_PATTERN)
        matcher = pattern.matcher(email)
        return matcher.matches()
    }

}