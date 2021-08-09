package com.dhopa.dhopaservice.sidemenu.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dhopa.dhopaservice.service_booking.fragments.CompletedServiceFragment
import com.dhopa.dhopaservice.service_booking.fragments.InProcessFragment
import com.dhopa.dhopaservice.sidemenu.fragments.ProgressServiceFragment

class AdapterViewPagerTrackingService (private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                return ProgressServiceFragment()
            }
            1 -> {
                return CompletedServiceFragment()
            }
            else -> return fragment!!
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}