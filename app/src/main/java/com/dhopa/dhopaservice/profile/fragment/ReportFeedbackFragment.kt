package com.dhopa.dhopaservice.profile.fragment

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
import com.dhopa.dhopaservice.databinding.FragmentRegisterBinding
import com.dhopa.dhopaservice.databinding.FragmentReportFeedbackBinding
import com.dhopa.dhopaservice.mvvm.CommonViewModel
import com.dhopa.dhopaservice.network.models.Details
import com.omninos.util_data.CommonUtils
import com.tapadoo.alerter.Alerter
import java.util.regex.Matcher
import java.util.regex.Pattern

class ReportFeedbackFragment : Fragment() {

    private lateinit var binding :FragmentReportFeedbackBinding
    private lateinit var view1 :View
    private lateinit var commonViewModel: CommonViewModel
    private var loginDetails: Details = App.appPreference1?.getUserDetails(
        AppConstants.USER_LOGIN_DETAILS, Details::class.java
    )!!
    private lateinit var strName:String
    private lateinit var strEmail:String
    private lateinit var strQuery:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReportFeedbackBinding.inflate(inflater, container, false)
        view1 =binding.root

        commonViewModel =
            ViewModelProviders.of(this@ReportFeedbackFragment).get(CommonViewModel::class.java)

        init()

        return view1
    }

    private fun init() {
        binding.btnSendQuery.setOnClickListener{validation()}
    }

    private fun validation() {
        strName=binding.etName.text.toString().trim()
        strEmail=binding.etEmail.text.toString().trim()
        strQuery=binding.etQuery.text.toString().trim()

        if (strName.isEmpty()) {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please enter name")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        } else if (strEmail.isEmpty()) {
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

        }
        else if (strQuery.isEmpty()) {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)

                .setText("Please enter query...")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        }
        else{
            sendData()
        }
    }

    private fun sendData() {
        CommonUtils.showProgress(requireActivity())
        val data: HashMap<String, String> = HashMap()
        data["userId"] = loginDetails.id
        data["name"] = strName
        data["email"] = strEmail
        data["feedback"] = strQuery

        commonViewModel.userFeedback(requireActivity(),data).observe(requireActivity(), Observer {
            if (it.success=="1"){
                binding.etName.text.clear()
                binding.etEmail.text.clear()
                binding.etQuery.text.clear()
                CommonUtils.dismissProgress()
                Alerter.create(activity)
                    .setTitle("Dhopa")
                    .setIcon(R.drawable.ic_notifications)
                    .setText(it.message)
                    .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                    .show()

            }else{
                CommonUtils.dismissProgress()
                Alerter.create(activity)
                    .setTitle("Dhopa")
                    .setIcon(R.drawable.ic_notifications)
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