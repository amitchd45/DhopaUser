package com.dhopa.dhopaservice.home.fragments

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Toast
import androidx.navigation.Navigation
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentSchedulePickUpBinding
import com.dhopa.dhopaservice.databinding.FragmentSchedulePickUpDateTimeBinding
import com.omninos.util_data.CommonUtils
import com.tapadoo.alerter.Alerter
import java.text.SimpleDateFormat
import java.util.*

class SchedulePickUpDateTimeFragment : Fragment(), DatePickerDialog.OnDateSetListener{

    private lateinit var binding: FragmentSchedulePickUpDateTimeBinding
    private lateinit var view1:View
    var day = 0
    var month: Int = 0
    var year: Int = 0
    var check: Int = 0
    private lateinit var type:String
    private var strPickUpTime:String=""
    private var strPickUpDate:String=""

    private var strDeliveryTime:String=""
    private var strDeliveryDate:String=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSchedulePickUpDateTimeBinding.inflate(inflater, container, false)

        view1=binding.root
        init()
        return view1
    }

    private fun init() {
        binding.btnContinuePay.setOnClickListener {
            validation()
        }

        binding.tvPickUpTime.setOnClickListener{
            type="1"
            setDate()
        }
        binding.tvPickDeliveryTime.setOnClickListener{
            type="0"
            deliveryDate()
        }

        binding.spPickUpTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position==0){

                }else{
                    strPickUpTime=parent.selectedItem.toString()
                    println("date strPickUpDate===$strPickUpDate")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        binding.spDeliveryTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position==0){

                }else{
                    strDeliveryTime=parent.selectedItem.toString()
                    println("date strPickUpDate===$strPickUpDate")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

    }

    private fun validation() {
        if (strPickUpDate==""){
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please select pickup date")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        }else if (strPickUpTime==""){
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please select pickup time")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        }else if (strDeliveryDate==""){
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please select delivery date")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        }else if (strDeliveryTime==""){
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please select delivery time")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        }else{
            Navigation.findNavController(view1).navigate(R.id.action_schedulePickUpDateTimeFragment_to_paymentFragment)
        }
    }

    private fun deliveryDate() {
        val calendar: Calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        val datePickerDialog = DatePickerDialog(requireContext(),  android.R.style.Theme_Holo_Light_Dialog,this@SchedulePickUpDateTimeFragment, year, month,day)
        datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show()
    }

    private fun setDate() {
        val calendar: Calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        val datePickerDialog = DatePickerDialog(requireContext(),  android.R.style.Theme_Holo_Light_Dialog,this@SchedulePickUpDateTimeFragment, year, month,day)
        datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        if (type=="1"){
            val date = Date(year-1900, month, dayOfMonth)
            val formatter = SimpleDateFormat("EE MMM d, yyyy")
            val formatter1 = SimpleDateFormat("yyyy-MM-dd")
            val mDate = formatter.format(date)
            val mDateServer = formatter1.format(date)

            println("Date : $mDate")
            println("Date1 : $mDateServer")
            binding.tvPickUpTime.text=mDate
            strPickUpDate=mDateServer
        }else{
            val date = Date(year-1900, month, dayOfMonth)
            val formatter = SimpleDateFormat("EE MMM d, yyyy")
            val formatter1 = SimpleDateFormat("yyyy-MM-dd")
            val mDate = formatter.format(date)
            val mDateServer = formatter1.format(date)

            println("Date : $mDate")
            println("Date1 : $mDateServer")
            binding.tvPickDeliveryTime.text=mDate
            strDeliveryDate=mDateServer
        }

    }


}
