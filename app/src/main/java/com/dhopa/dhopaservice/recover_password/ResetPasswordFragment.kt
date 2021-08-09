package com.dhopa.dhopaservice.recover_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.beaute.user.SharePrefrence.AppConstants
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentResetPasswordBinding
import com.dhopa.dhopaservice.mvvm.CommonViewModel
import com.omninos.util_data.CommonUtils
import com.tapadoo.alerter.Alerter

class ResetPasswordFragment : Fragment() {

    private lateinit var binding:FragmentResetPasswordBinding
    private lateinit var commonViewModel: CommonViewModel
    private lateinit var strNewPassword: String
    private lateinit var strConfirmPassword: String
    private lateinit var strEmail: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)

        commonViewModel =
            ViewModelProviders.of(this@ResetPasswordFragment).get(CommonViewModel::class.java)
        strEmail=arguments?.getString(AppConstants.USER_EMAIL).toString()

        init()
        
        return binding.root
    }

    private fun init(){
        binding.btnConfirm.setOnClickListener { validation() }
    }

    private fun validation() {
        strNewPassword=binding.etNewPassword.text.toString().trim()
        strConfirmPassword=binding.etConfirmPassword.text.toString().trim()

        if (strNewPassword.isEmpty()) {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)

                .setText("Please enter Password...")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        } else if (strNewPassword != strConfirmPassword) {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("password mismatch")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()

        } else {
            updatePassword()
        }
    }

    private fun updatePassword() {
        CommonUtils.showProgress(requireActivity())
        val data: HashMap<String, String> = HashMap()
        data["email"] = strEmail
        data["password"] = strNewPassword
        commonViewModel.resetPassword(requireActivity(),data).observe(requireActivity(), Observer {
            if (it.success=="1"){
                CommonUtils.dismissProgress()
                Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
            }else{
                CommonUtils.dismissProgress()
                Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
            }
        })
    }
}