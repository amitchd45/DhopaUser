package com.dhopa.dhopaservice.profile.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.beaute.user.SharePrefrence.App
import com.beaute.user.SharePrefrence.AppConstants
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentAddressFieldBinding
import com.dhopa.dhopaservice.mvvm.CommonViewModel
import com.dhopa.dhopaservice.network.models.Details
import com.dhopa.dhopaservice.network.models.GetPostalCodeModel
import com.omninos.util_data.CommonUtils
import com.tapadoo.alerter.Alerter


class AddressFieldFragment : Fragment() {
    private lateinit var binding: FragmentAddressFieldBinding
    private lateinit var commonViewModel:CommonViewModel
    private lateinit var strPostalCode: String
    private lateinit var strAddressLine1: String
    private lateinit var strAddressLine2: String
    private lateinit var strCity: String
    private lateinit var strCountry: String
//    private lateinit var listPostal:List<GetPostalCodeModel>
    private lateinit var listPostal:List<String>
    private  var strType: String=""
    private  var loginDetails: Details = App.appPreference1?.getUserDetails(
        AppConstants.USER_LOGIN_DETAILS,
        Details::class.java
    )!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddressFieldBinding.inflate(inflater, container, false)

        commonViewModel = ViewModelProviders.of(this@AddressFieldFragment).get(CommonViewModel::class.java)

        init()
        getPostalCode()

        return binding.root
    }

    private fun getPostalCode() {
        commonViewModel.getPostalCode(requireActivity()).observe(requireActivity(), Observer {
            if (it.postcode!==null){
                Toast.makeText(requireContext(), it.postcode, Toast.LENGTH_SHORT).show()
                binding.etPostalCode.setText(it.postcode)
                binding.etAddressLine1.setText(it.addresses[0].line_1)
                binding.etAddressLine2.setText(it.addresses[0].line_2)
                binding.etCityTown.setText(it.addresses[0].town_or_city)
                binding.etCountry.setText(it.addresses[0].country)
            }
        })
    }

    private fun init() {
        binding.btnSave.setOnClickListener {
            validation()
        }

        binding.radioBtnHotel.setOnCheckedChangeListener{ button: CompoundButton, b: Boolean ->
            if (b){
                strType="Hotel"
            }
        }

        binding.radioBtnHome.setOnCheckedChangeListener{ button: CompoundButton, b: Boolean ->
            if (b){
                strType="Home"
            }
        }

        binding.radioBtnOffice.setOnCheckedChangeListener{ button: CompoundButton, b: Boolean ->
            if (b){
                strType="Office"
            }
        }
    }

    private fun validation() {
        strPostalCode=binding.etPostalCode.text.toString().trim()
        strAddressLine1=binding.etAddressLine1.text.toString().trim()
        strAddressLine2=binding.etAddressLine2.text.toString().trim()
        strCity=binding.etCityTown.text.toString().trim()
        strCountry=binding.etCountry.text.toString().trim()

        when {
            strPostalCode.isEmpty() -> {
                Alerter.create(activity)
                    .setTitle("Dhopa")
                    .setIcon(R.drawable.ic_warning)
                    .setText("Please enter postal code")
                    .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                    .show()
            }
            strAddressLine1.isEmpty() -> {
                Alerter.create(activity)
                    .setTitle("Dhopa")
                    .setIcon(R.drawable.ic_warning)
                    .setText("Please enter address line1")
                    .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                    .show()
            }
            strCity.isEmpty() -> {
                Alerter.create(activity)
                    .setTitle("Dhopa")
                    .setIcon(R.drawable.ic_warning)
                    .setText("Please enter city or town name...")
                    .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                    .show()
            }
            strCountry.isEmpty() -> {
                Alerter.create(activity)
                    .setTitle("Dhopa")
                    .setIcon(R.drawable.ic_warning)
                    .setText("Please enter country name...")
                    .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                    .show()
            }
            else -> {
                addAddress()
            }
        }
    }

    private fun addAddress() {
        CommonUtils.showProgress(requireActivity())
        val data: HashMap<String, String> = HashMap()

        data["userId"] = loginDetails.id
        data["type"] = strType
        data["postalCode"] = strPostalCode
        data["address1"] = strAddressLine1
        data["address2"] = strAddressLine2
        data["city"] = strCity
        data["country"] = strCountry
        data["latitude"] = "0.0"
        data["longitude"] = "0.0"


        commonViewModel.addUserAddress(requireActivity(),data).observe(requireActivity(), Observer {
            if (it.success=="1"){
                CommonUtils.dismissProgress()
                Alerter.create(activity)
                    .setTitle("Dhopa")
                    .setIcon(R.drawable.ic_warning)
                    .setText(it.message)
                    .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                    .show()

                requireActivity().onBackPressed()

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