package com.dhopa.dhopaservice.profile.fragment

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.beaute.user.SharePrefrence.App
import com.beaute.user.SharePrefrence.AppConstants
import com.bumptech.glide.Glide
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentAccountInfoBinding
import com.dhopa.dhopaservice.mvvm.CommonViewModel
import com.dhopa.dhopaservice.network.models.Details
import com.dhopa.dhopaservice.profile.models.UserDetails
import com.github.dhaval2404.imagepicker.ImagePicker
import com.omninos.util_data.CommonUtils
import com.tapadoo.alerter.Alerter
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AccountInfoFragment : Fragment() {

    private lateinit var binding: FragmentAccountInfoBinding
    private lateinit var view1: View
    private lateinit var commonViewModel: CommonViewModel
    private var imagePath: String = ""
    private var strFirstName: String = ""
    private var strLastName: String = ""
    private var strPhone: String = ""
    private var strEmail: String = ""
    private var strValueParameter: String = ""
    private var userId: String = ""
    private var loginDetails: Details = App.appPreference1?.getUserDetails(AppConstants.USER_LOGIN_DETAILS,Details::class.java)!!
    private var userDetails: UserDetails = App.appPreference1?.getUserDetails(AppConstants.USER_PROFILE_DATA, UserDetails::class.java)!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountInfoBinding.inflate(inflater, container, false)
        view1 = binding.root
        commonViewModel = ViewModelProviders.of(this@AccountInfoFragment).get(CommonViewModel::class.java)

        userId = loginDetails.id
        init()

        if (userDetails!=null){
            Glide.with(requireActivity()).load(userDetails.image).placeholder(R.drawable.userimage).into(binding.imageView)
            binding.tvHeadName.text=userDetails.name
            binding.tvHeadLastName.text=userDetails.lastName
            binding.tvUserFirstName.text=userDetails.name
            binding.tvUserLastName.text=userDetails.lastName
            binding.tvPhoneNumber.text=userDetails.phone
            binding.tvUserEmail.text=userDetails.email
        }

        return view1
    }

    private fun init() {
        binding.tvFirstName.setOnClickListener {
            strValueParameter = "name"
            updateUserDetailsDialog("1", strValueParameter)
        }

        binding.tvLastName.setOnClickListener {
            strValueParameter = "lastName"
            updateUserDetailsDialog("2", strValueParameter)
        }
        binding.tvPhone.setOnClickListener {
            strValueParameter = "phone"
            updateUserDetailsDialog("3", strValueParameter)
        }
        binding.tvEmail.setOnClickListener {
            strValueParameter = "email"
            updateUserDetailsDialog("4", strValueParameter)
        }

        binding.imageView.setOnClickListener {
            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

    }

    private fun updateUserDetailsDialog(type: String, valueParameter: String) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        var input = EditText(requireActivity())
        builder.setTitle(" ")
        when (type) {
            "1" -> {
                input.hint = "Enter first name"
                input.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
                builder.setView(input)
            }
            "2" -> {
                input.hint = "Enter last name"
                input.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
                builder.setView(input)
            }
            "3" -> {
                input.hint = "Enter phone number"
                input.inputType = InputType.TYPE_CLASS_PHONE
                builder.setView(input)
            }
            "4" -> {
                input.hint = "Enter email address"
                input.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                builder.setView(input)
            }
        }
        builder.setPositiveButton("Save", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            var mText = input.text.toString().trim()
            if (mText.isEmpty()) {
                Alerter.create(activity)
                    .setTitle("Dhopa")
                    .setIcon(R.drawable.ic_warning)
                    .setText("Please enter your detail...")
                    .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                    .show()
            } else {
                updateData(userId, mText, valueParameter)
            }
        })
        builder.setNegativeButton(
            "Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    private fun updateData(userId: String, mValue: String, valueParameter: String) {

        val data: HashMap<String, String> = HashMap()
        data["userId"] = userId
        data["value"] = mValue
        data["valueParameter"] = valueParameter

        commonViewModel.updateUserProfile(requireActivity(), data).observe(requireActivity(),
            Observer {
                if (it.success == "1") {
                    if (valueParameter == "name") {
                        binding.tvUserFirstName.text = it.details.name
                        binding.tvHeadName.text = it.details.name
                    }
                    if (valueParameter == "lastName") {
                        binding.tvUserLastName.text = it.details.lastName
                        binding.tvHeadLastName.text = it.details.lastName
                    }
                    if (valueParameter == "phone") {
                        binding.tvPhoneNumber.text = it.details.phone
                    }
                    if (valueParameter == "email") {
                        binding.tvUserEmail.text = it.details.email
                    }

                    Alerter.create(activity)
                        .setTitle("Dhopa")
                        .setText(it.message)
                        .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                        .show()

                } else {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
//            userImage.setImageURI(fileUri)

            imagePath = fileUri?.path.toString()

            updateData(userId, imagePath)

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }


    private fun updateData(
        id: String,
        imagePath: String
    ) {

        val data: HashMap<String, String> = HashMap()
        data["userId"] = id
        data["image"] = imagePath

        val body: MultipartBody.Part
        val file = File(this.imagePath)


        body = if (this.imagePath != "") {
            val requestFile: RequestBody =
                file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", file.name, requestFile)
        } else {
            val requestFile = "".toRequestBody("multipart/form-data".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", file.name, requestFile)
        }
        val userId: RequestBody = id.toRequestBody("text/plain".toMediaType())

        commonViewModel.updateImage(
            requireActivity(),
            userId,
            CommonUtils.getImgdData(imagePath, "image")
        ).observe(requireActivity(),
            Observer {
                if (it.success == "1") {
//                    this.imagePath = ""
//                    this.strFirstName = ""
//                    this.strLastName = ""
//                    this.strPhone = ""
//                    this.strEmail = ""

                    Glide.with(requireActivity()).load(it.details.image).placeholder(R.drawable.ic_add).into(binding.imageView)

                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            })
    }
}
