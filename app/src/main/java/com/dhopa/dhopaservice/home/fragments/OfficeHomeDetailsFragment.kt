package com.dhopa.dhopaservice.home.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.beaute.user.SharePrefrence.App
import com.beaute.user.SharePrefrence.AppConstants
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.mvvm.CommonViewModel
import com.dhopa.dhopaservice.network.models.CitiesListModel
import com.dhopa.dhopaservice.network.models.CityList
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.button.MaterialButton
import com.omninos.util_data.CommonUtils
import com.tapadoo.alerter.Alerter
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.fragment_office_home_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
class OfficeHomeDetailsFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var btn_bookNow: MaterialButton
    private lateinit var tv_pinLocation: TextView
    private lateinit var view1: View
    private lateinit var rb_home: RadioButton
    private lateinit var rb_office: RadioButton
    private lateinit var rb_hotel: RadioButton
    private var forService: String = ""
    private var strName: String = ""
    private var strEmail: String = ""
    private var strPhone: String = ""
    private var locationId: String = ""
    private var strFullAddress: String = ""

    private lateinit var et_fullName: EditText
    private lateinit var et_email: EditText
    private lateinit var et_phone: EditText

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null
    private var latitudeLabel: String? = null
    private var longitudeLabel: String? = null
    private var latitudeText: TextView? = null
    private var longitudeText: TextView? = null
    private lateinit var tv_full_address: TextView
    private lateinit var commonViewModel: CommonViewModel
    private var list: MutableList<CityList> = mutableListOf()
    private var locationList: MutableList<String> = mutableListOf()
    private lateinit var sp_location: SearchableSpinner

    companion object {
        private val TAG = "LocationProvider"
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_office_home_details, container, false)
        commonViewModel =
            ViewModelProviders.of(this@OfficeHomeDetailsFragment).get(CommonViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        init()
            getLocationList()

        return view1
    }

    private fun getLocationList() {
        commonViewModel.citiesList(requireActivity()).observe(
            requireActivity(),
            androidx.lifecycle.Observer {
                if (it.success == "1") {
                    list = it.details as MutableList<CityList>
                    locationList.clear()
                    sp_location.setTitle("Select Location")
                    locationList.add("Select Location")
                    for (i in 0 until list.size) {
                        locationList.add(list[i].name)
                    }
                    try {
                        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            requireActivity(),
                            android.R.layout.simple_spinner_dropdown_item,
                            locationList
                        )
                        sp_location.adapter = adapter

                    } catch (e: Exception) {

                    }
                } else {
                    CommonUtils.ShowMsg(requireActivity(), it.message)
                }

                sp_location.onItemSelectedListener = this
            })
    }

    private fun init() {
        sp_location = view1.findViewById(R.id.sp_location)
        tv_full_address = view1.findViewById(R.id.tv_full_address)
        et_fullName = view1.findViewById(R.id.et_fullName)
        et_email = view1.findViewById(R.id.et_email)
        et_phone = view1.findViewById(R.id.et_phone)

        btn_bookNow = view1.findViewById(R.id.btn_bookNow)
        tv_pinLocation = view1.findViewById(R.id.tv_pinLocation)
        rb_home = view1.findViewById(R.id.rb_home)
        rb_office = view1.findViewById(R.id.rb_office)
        rb_hotel = view1.findViewById(R.id.rb_hotel)

        tv_pinLocation.setOnClickListener {
            Navigation.findNavController(view1)
                .navigate(R.id.action_officeHomeDetailsFragment_to_pinYourLocationFragment)
        }
        btn_bookNow.setOnClickListener {
//            Navigation.findNavController(view1).navigate(R.id.action_officeHomeDetailsFragment_to_schedulePickUpFragment)
            validation()
        }
        rb_home.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                forService = "home"
                rb_office.isChecked = false
                rb_hotel.isChecked = false
                CommonUtils.ShowMsg(requireActivity(), forService)
            }
        }
        rb_office.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                forService = "office"
                rb_home.isChecked = false
                rb_hotel.isChecked = false
                CommonUtils.ShowMsg(requireActivity(), forService)
            }
        }
        rb_hotel.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                forService = "hotel"
                rb_home.isChecked = false
                rb_office.isChecked = false
                CommonUtils.ShowMsg(requireActivity(), forService)
            }
        }
    }

    private fun validation() {
        strName = et_fullName.text.toString().trim()
        strEmail = et_email.text.toString().trim()
        strPhone = et_phone.text.toString().trim()

        if (strName.isEmpty()) {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please enter full name...")
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
        } else if (!emailValidator(strEmail)) {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please enter valid email")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        } else if (forService == "") {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please select option for service(i.e home,office,hotel)")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        }else if (locationId == "") {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please select location")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        }else if (strFullAddress == "") {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please check your complete address")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        } else {
            Navigation.findNavController(view1)
                .navigate(R.id.action_officeHomeDetailsFragment_to_schedulePickUpFragment)
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

    override fun onStart() {
        super.onStart()
        if (!checkPermissions()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions()
            }
        } else {
            getLastLocation()
        }

        CommonUtils.ShowMsg(requireActivity(), "onStart")
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient?.lastLocation!!.addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful && task.result != null) {
                lastLocation = task.result
                getFullAddress(
                    lastLocation!!.latitude,
                    lastLocation!!.longitude
                )
            } else {
                Log.w(TAG, "getLastLocation:exception", task.exception)
                CommonUtils.ShowMsg(
                    requireActivity(),
                    "No location detected. Make sure location is enabled on the device."
                )
            }
        }
    }

    private fun getFullAddress(latitude: Double, longitude: Double) {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(requireActivity(), Locale.getDefault())

        addresses = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        )
        val address: String = addresses[0].getAddressLine(0)
        App.appPreference1.SaveString(AppConstants.ADDRESS,address)
        tv_full_address.text = address

        strFullAddress=address
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            showSnackbar("Location permission is needed for core functionality", "Okay",
                View.OnClickListener {
                    startLocationPermissionRequest()
                })
        } else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.i(TAG, "User interaction was cancelled.")
                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    // Permission granted.
                    getLastLocation()
                }
                else -> {
                    showSnackbar("Permission was denied", "Settings",
                        View.OnClickListener {
                            // Build intent that displays the App settings screen.
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts(
                                "package",
                                Build.DISPLAY, null
                            )
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }

    private fun showSnackbar(
        mainTextStringId: String, actionStringId: String,
        listener: View.OnClickListener
    ) {
        Toast.makeText(requireContext(), mainTextStringId, Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        if (App.appPreference1.GetString(AppConstants.SCREEN_STATUS) != null) {
            if (App.appPreference1.GetString(AppConstants.SCREEN_STATUS).equals("1")) {
                if (App.appPreference1.GetString(AppConstants.ADDRESS) != null) {
                    tv_full_address.text = App.appPreference1.GetString(AppConstants.ADDRESS)
                    strFullAddress = App.appPreference1.GetString(AppConstants.ADDRESS)
                }
                App.appPreference1.SaveString(AppConstants.SCREEN_STATUS, "0")
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position == 0) {

        } else {
            locationId = list[position].id
            CommonUtils.ShowMsg(requireActivity(), list[position].name + "\n" + "ID :" + locationId)
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}