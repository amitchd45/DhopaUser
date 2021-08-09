package com.dhopa.dhopaservice.home.fragments

import android.Manifest
import android.app.Activity
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.beaute.user.SharePrefrence.App
import com.beaute.user.SharePrefrence.AppConstants
import com.dhopa.dhopaservice.R
import com.google.android.gms.location.LocationListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.omninos.util_data.CommonUtils
import java.io.IOException
import java.util.*

class PinYourLocationFragment : Fragment(), OnMapReadyCallback {

    private lateinit var view1:View
    private lateinit var apikey:String
    private lateinit var map: GoogleMap
    private lateinit var mLocationManager: LocationManager;
//    private var lat: Double = 30.7333
    private var lat: Double = 0.0
    private var newlat: Double = 0.0
    private var lng: Double = 0.0
//    private var lng: Double = 76.7794
    private var newlng: Double = 0.0
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    var check: Int = 0
    private lateinit var myLatLng: LatLng
    private lateinit var locationSearch:TextView
    private lateinit var btnSetAddress:Button
    private  var address:String=""
    private lateinit var btnCurrentLocation: FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_pin_your_location, container, false)

        ids()
        getLiveLocation()
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.map_home1) as SupportMapFragment?
        supportMapFragment!!.getMapAsync(this)

        apikey = getString(R.string.map_key)

        if (!Places.isInitialized()) {
            Places.initialize(requireActivity(), apikey)
        }

        return view1
    }

    override fun onMapReady(p0: GoogleMap?) {
        if (p0 != null) {
            map = p0
        }
        if (map != null) {
            map.clear()
        }
        val sydney = LatLng(lat, lng)
        myLatLng = LatLng(lat, lng)
        map.addMarker(
            MarkerOptions().position(sydney).title(""))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    lat,
                    lng
                ), 12.0f
            )
        )
    }

    private fun ids() {
        btnCurrentLocation=view1.findViewById(R.id.btnCurrentLocation)
        btnSetAddress=view1.findViewById(R.id.btnSetAddress)
        locationSearch=view1.findViewById(R.id.et_location_search)
        locationSearch.setOnClickListener{
            locationSearch()
        }
        btnCurrentLocation.setOnClickListener{
            getLiveLocation()
        }
        btnSetAddress.setOnClickListener{
            App.appPreference1.SaveString(AppConstants.SCREEN_STATUS,"1")
            App.appPreference1.SaveString(AppConstants.LAT,lat.toString())
            App.appPreference1.SaveString(AppConstants.LON,lng.toString())
            App.appPreference1.SaveString(AppConstants.ADDRESS,address)
            requireActivity().onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()

    }

    private fun getLiveLocation() {
        var check = 1
        if (isLocationEnabled()) {
            mLocationManager = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
            if (ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_COARSE_LOCATION
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
            }
            mLocationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0,
                0.99f,
                android.location.LocationListener { location ->
                    lat = location.latitude
                    lng = location.longitude
                    newlat = location.latitude
                    newlng = location.longitude
                    val activity: Activity? = activity
                    if (activity != null && isAdded) {
                        try {
                            val geo = Geocoder(requireActivity(), Locale.getDefault())
                            val addresses =
                                geo.getFromLocation(lat, lng, 1)
                            if (addresses.isEmpty()) {
                                Toast.makeText(
                                    activity, "Waiting for location", Toast.LENGTH_SHORT).show()
                            } else {
                                if (check == 1) {
                                    check = 0
                                    val supportMapFragment = childFragmentManager.findFragmentById(R.id.map_home1) as SupportMapFragment?
                                    supportMapFragment!!.getMapAsync(this)
                                }
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                })
        }
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager =
            requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun locationSearch() {
        val fields =
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )
        val intent =
            Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(requireActivity())
        startActivityForResult(
            intent,
            AUTOCOMPLETE_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            map.clear()
            val place = Autocomplete.getPlaceFromIntent(data!!)
            address = place.address.toString()
            lat = place.latLng!!.latitude
            lng = place.latLng!!.longitude

            locationSearch.text=address
            CommonUtils.ShowMsg(requireActivity(),address)

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            val status = Autocomplete.getStatusFromIntent(data!!)
            Toast.makeText(activity, "Error: " + status.statusMessage, Toast.LENGTH_LONG).show()
//            Log.i("status", status.statusMessage)
        } else if (resultCode == Activity.RESULT_CANCELED) {
// The user canceled the operation.
        }
    }


}