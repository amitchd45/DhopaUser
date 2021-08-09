package com.dhopa.dhopaservice.profile.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.beaute.user.SharePrefrence.App
import com.beaute.user.SharePrefrence.AppConstants
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentCreatePasswordBinding
import com.dhopa.dhopaservice.mvvm.CommonViewModel
import com.dhopa.dhopaservice.network.models.Details
import com.dhopa.dhopaservice.network.models.LoginRegisterModel
import com.omninos.util_data.CommonUtils
import com.tapadoo.alerter.Alerter

class CreatePasswordFragment : Fragment(){
    private lateinit var binding : FragmentCreatePasswordBinding
    private lateinit var view1 : View
    private lateinit var commonViewModel: CommonViewModel
    private lateinit var strOldPassword: String
    private lateinit var strNewPassword: String
    private lateinit var strConfirmPassword: String
    private  var loginDetails: Details = App.appPreference1?.getUserDetails(AppConstants.USER_LOGIN_DETAILS,Details::class.java)!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreatePasswordBinding.inflate(inflater, container, false)
        view1 =binding.root

        commonViewModel = ViewModelProviders.of(this@CreatePasswordFragment).get(CommonViewModel::class.java)

        init()

        return view1
    }

    private fun init(){
        binding.btnDone.setOnClickListener{
            validation()
        }
    }

    private fun validation() {
        strOldPassword = binding.etOldPassword.text.toString().trim()
        strNewPassword = binding.etNewPassword.text.toString().trim()
        strConfirmPassword = binding.etConfirmPassword.text.toString().trim()

        if (strOldPassword.isEmpty()) {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please enter old password...")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        }else if (strNewPassword.isEmpty()) {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please enter new password...")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        }else if (strNewPassword != strConfirmPassword) {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)

                .setText("password mismatch")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()

        } else {
            changePassword()
        }
    }

    private fun changePassword() {
        CommonUtils.showProgress(requireActivity())
        val data: HashMap<String, String> = HashMap()
        data["userId"] = loginDetails.id
        data["oldPassword"] = strOldPassword
        data["newPassword"] = strNewPassword

        commonViewModel.changePassword(requireActivity(),data).observe(requireActivity(), Observer {
            if (it.success=="1"){
                CommonUtils.dismissProgress()
                Alerter.create(activity)
                    .setTitle("Dhopa")
                    .setIcon(R.drawable.ic_warning)
                    .setText(it.message)
                    .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                    .show()
            }else{
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