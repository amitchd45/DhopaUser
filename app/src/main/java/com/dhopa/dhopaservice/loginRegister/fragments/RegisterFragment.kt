package com.dhopa.dhopaservice.loginRegister.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.beaute.user.SharePrefrence.AppConstants
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentRegisterBinding
import com.dhopa.dhopaservice.mvvm.CommonViewModel
import com.omninos.util_data.CommonUtils
import com.tapadoo.alerter.Alerter
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var view1: View
    private lateinit var commonViewModel: CommonViewModel
    private lateinit var strName: String
    private lateinit var strEmail: String
    private lateinit var strPhone: String
    private lateinit var strPassword: String
    private lateinit var strConfirnPassword: String

    //    private lateinit var ccp: CountryCodePicker
    private lateinit var countryCode: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        commonViewModel =
            ViewModelProviders.of(this@RegisterFragment).get(CommonViewModel::class.java)
        view1 = binding.root

        init()

        return view1
    }

    private fun init() {
        binding.btnRegister.setOnClickListener {
            validation()
        }

        binding.tvHaveAcc.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun validation() {
        countryCode = "+91"
        strName = binding.etUser.text.toString().trim()
        strEmail = binding.etEmail.text.toString().trim()
        strPhone = binding.etPhone.text.toString().trim()
        strPassword = binding.etPassword.text.toString().trim()
        strConfirnPassword = binding.etConfirmPassword.text.toString().trim()

        if (strName.isEmpty()) {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please enter name...")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        } else if (strPhone.isEmpty()) {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)

                .setText("Please enter Phone Number...")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        } else if (strEmail.isEmpty()) {

            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please enter email")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()

//
        } else if (!emailValidator(strEmail)) {

            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please enter valid email")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()

        }
        else if (strPassword.isEmpty()) {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)

                .setText("Please enter Password...")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        } else if (strPassword != strConfirnPassword) {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)

                .setText("password mismatch")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()

        } else {
            checkPhoneEmail()
        }
    }

    private fun checkPhoneEmail() {
        CommonUtils.showProgress(requireActivity())
        val data: HashMap<String, String> = HashMap()
        data["email"] = strEmail
        data["phone"] = "+91$strPhone"

        commonViewModel.checkPhoneEmail(requireActivity(), data)
            .observe(requireActivity(), Observer {
                if (it.success == "1") {
                    CommonUtils.dismissProgress()
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                    Alerter.create(activity)
                        .setTitle("Dhopa")
                        .setIcon(R.drawable.ic_warning)
                        .setText("OTP : "+it.otp)
                        .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                        .show()

                    val bundle = Bundle()
                    bundle.putString(AppConstants.USER_NAME, strName)
                    bundle.putString(AppConstants.USER_EMAIL, strEmail)
                    bundle.putString(AppConstants.USER_PHONE, strPhone)
                    bundle.putString(AppConstants.USER_PASSWORD, strPassword)
                    bundle.putString(AppConstants.USER_OTP, it.otp)

                    view1.findNavController().navigate(R.id.action_registerFragment_to_otpVerificationFragment,bundle)


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