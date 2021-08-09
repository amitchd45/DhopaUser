package com.dhopa.dhopaservice.profile.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.beaute.user.SharePrefrence.App
import com.beaute.user.SharePrefrence.AppConstants
import com.bumptech.glide.Glide
import com.dhopa.dhopaservice.MainActivity
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentProfileBinding
import com.dhopa.dhopaservice.mvvm.CommonViewModel
import com.dhopa.dhopaservice.network.models.Details
import com.omninos.util_data.CommonUtils
import com.tapadoo.alerter.Alerter

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var view1: View
    private lateinit var commonViewModel: CommonViewModel
    private var loginDetails: Details = App.appPreference1?.getUserDetails(
        AppConstants.USER_LOGIN_DETAILS, Details::class.java
    )!!

     interface Select{
        fun sendDetails(image: String, name: String);
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        view1 = binding.root
        commonViewModel =
            ViewModelProviders.of(this@ProfileFragment).get(CommonViewModel::class.java)

        init()
        delayedHide(3000)
        return view1
    }

    private fun delayedHide(delayMillis: Int) {
        Handler().postDelayed({
            getProfileData(loginDetails.id)
        }, delayMillis.toLong())
    }

    private fun getProfileData(userId: String) {
//        CommonUtils.showProgress(requireActivity())
        val data: HashMap<String, String> = HashMap()
        data["userId"] = userId

        commonViewModel.userProfile(requireActivity(), data).observe(requireActivity(), Observer {
            if (it.success == "1") {
                binding.shimmerFrameLayout.stopShimmerAnimation()
                binding.shimmerFrameLayout.visibility = View.GONE
                binding.rlTopProfileSection.visibility = View.VISIBLE
                App.appPreference1?.saveUserDetails(AppConstants.USER_PROFILE_DATA,it.details)

//                select.sendDetails(it.details.image, it.details.name)

                if (it.details != null) {
                    Glide.with(requireActivity()).load(it.details.image)
                        .placeholder(R.drawable.userimage).into(
                        binding.ivImage
                    )
                    binding.tvName.text = it.details.name + " " + it.details.lastName
                }

            } else {
                binding.shimmerFrameLayout.visibility = View.GONE
                CommonUtils.ShowMsg(requireActivity(), it.message)
            }
        })
    }

    private fun init() {
        binding.tvRefer.setOnClickListener {
            Navigation.findNavController(view1)
                .navigate(R.id.action_profileFragment_to_referAndEarnFragment)
        }
        binding.tvAccountInfo.setOnClickListener {
            Navigation.findNavController(view1)
                .navigate(R.id.action_profileFragment_to_accountInfoFragment)
        }

        binding.tvCreatePassword.setOnClickListener {
            if (App.appPreference1.isLogin(requireContext())) {
                Navigation.findNavController(view1)
                    .navigate(R.id.action_profileFragment_to_createPasswordFragment)
            } else {
                Alerter.create(activity)
                    .setTitle("Dhopa")
                    .setIcon(R.drawable.ic_warning)
                    .setText("You are not logged in...")
                    .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                    .show()
            }
        }

        binding.tvAddress.setOnClickListener {
            if (App.appPreference1.isLogin(requireContext())) {
                Navigation.findNavController(view1)
                    .navigate(R.id.action_profileFragment_to_myAddressesFragment)
            } else {
                Alerter.create(activity)
                    .setTitle("Dhopa")
                    .setIcon(R.drawable.ic_warning)
                    .setText("You are not logged in...")
                    .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                    .show()
            }
        }

        binding.tvReportFeedback.setOnClickListener {
            Navigation.findNavController(view1)
                .navigate(R.id.action_profileFragment_to_reportFeedbackFragment)
        }
        binding.tvLogout.setOnClickListener {
            logoutDialog()
        }
        binding.tvRefer.setOnClickListener {
            invite()
        }
    }

    private fun logoutDialog() {
        val builder = AlertDialog.Builder(requireActivity())
        //set title for alert dialog
        builder.setTitle(R.string.dialogTitle)
        //set message for alert dialog
        builder.setMessage(R.string.dialogMessage)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            logoutFromApp()
        }

        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun logoutFromApp() {
        CommonUtils.showProgress(requireActivity())
        val data: HashMap<String, String> = HashMap()
        data["userId"] = loginDetails.id
        commonViewModel.logout(requireActivity(), data).observe(requireActivity(), Observer {
            if (it.success == "1") {
                App.appPreference1.Logout()
                CommonUtils.dismissProgress()
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                CommonUtils.dismissProgress()
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        binding.shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }

    private fun invite(){
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val shareMessage =
            "Hello there, \n Amit invited you to join Dhopa.\n\nDownload Dhopa from PlayStore.And Use My Promo code to Register \n\n Promo code is : CFDSGFH765"
        val msg =
            "$shareMessage\n https://play.google.com/store/apps/details?id=com.dhopa.dhopaservice&hl=en"
        shareIntent.putExtra(Intent.EXTRA_TEXT, msg)
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }
}