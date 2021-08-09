package com.dhopa.dhopaservice.mvvm

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhopa.dhopaservice.network.ApiClient
import com.dhopa.dhopaservice.network.ApiInterface
import com.dhopa.dhopaservice.network.ApiPostalCode
import com.dhopa.dhopaservice.network.models.*
import com.dhopa.dhopaservice.profile.models.GetUserAddressModel
import com.dhopa.dhopaservice.profile.models.UpdateProfileModel
import com.dhopa.dhopaservice.profile.models.UserProfileModel
import com.omninos.util_data.CommonUtils
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommonViewModel : ViewModel() {
    private val apiInterface = ApiClient.getClient()?.create(ApiInterface::class.java)
    private val apiInterface1 = ApiPostalCode.getClient1()?.create(ApiInterface::class.java)

    private lateinit var userRegister: MutableLiveData<LoginRegisterModel>

    fun register(
        activity: Activity,
        data: HashMap<String, String>,
    ): LiveData<LoginRegisterModel> {
        userRegister = MutableLiveData<LoginRegisterModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                apiInterface?.register(data)?.enqueue(object : Callback<LoginRegisterModel> {
                    override fun onFailure(call: Call<LoginRegisterModel>, t: Throwable) {
                        Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(
                        call: Call<LoginRegisterModel>,
                        response: Response<LoginRegisterModel>,
                    ) {
                        if (response.body() != null) {
                            userRegister.value = response.body()
                        }
                    }
                })
            }
            else -> {
                CommonUtils.dismissProgress()
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return userRegister
    }
    // user login
    private lateinit var userLogin: MutableLiveData<LoginRegisterModel>

    fun login(
        activity: Activity,
        data: HashMap<String, String>,
    ): LiveData<LoginRegisterModel> {
        userLogin = MutableLiveData<LoginRegisterModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                apiInterface?.login(data)?.enqueue(object : Callback<LoginRegisterModel> {
                    override fun onFailure(call: Call<LoginRegisterModel>, t: Throwable) {
                        Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(
                        call: Call<LoginRegisterModel>,
                        response: Response<LoginRegisterModel>,
                    ) {
                        if (response.body() != null) {
                            userLogin.value = response.body()
                        }
                    }
                })
            }
            else -> {
                CommonUtils.dismissProgress()
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return userLogin
    }
    // Social Register
    private lateinit var socialRegisterData: MutableLiveData<LoginRegisterModel>
    fun socialRegisterDetails(
        activity: Activity,
        data: HashMap<String, String>
    ): LiveData<LoginRegisterModel> {
        socialRegisterData = MutableLiveData<LoginRegisterModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                CommonUtils.showProgress(activity)
                apiInterface?.socialLogin(
                    data
                )
                    ?.enqueue(object : Callback<LoginRegisterModel> {
                        override fun onResponse(
                            call: Call<LoginRegisterModel>,
                            response: Response<LoginRegisterModel>
                        ) {
                            CommonUtils.dismissProgress()
                            if (response.isSuccessful) {
                                socialRegisterData.value = response.body()
                            } else {
                                Toast.makeText(activity, "response error", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                        override fun onFailure(
                            call: Call<LoginRegisterModel>,
                            t: Throwable
                        ) {
                            CommonUtils.dismissProgress()
                            Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                        }
                    })
            }
            else -> {
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return socialRegisterData
    }

    // check phone email
    private lateinit var checkPhoneEmail: MutableLiveData<CheckPhoneEmailModel>
    fun checkPhoneEmail(
        activity: Activity,
        data: HashMap<String, String>,
    ): LiveData<CheckPhoneEmailModel> {
        checkPhoneEmail = MutableLiveData<CheckPhoneEmailModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                apiInterface?.checkPhoneEmail(data)
                    ?.enqueue(object : Callback<CheckPhoneEmailModel> {
                        override fun onFailure(call: Call<CheckPhoneEmailModel>, t: Throwable) {
                            Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(
                            call: Call<CheckPhoneEmailModel>,
                            response: Response<CheckPhoneEmailModel>,
                        ) {
                            if (response.body() != null) {
                                checkPhoneEmail.value = response.body()
                            }
                        }
                    })
            }
            else -> {
                CommonUtils.dismissProgress()
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return checkPhoneEmail
    }
    // forgot password
    private lateinit var forgotPass: MutableLiveData<CheckPhoneEmailModel>
    fun forgotPassword(
        activity: Activity,
        data: HashMap<String, String>,
    ): LiveData<CheckPhoneEmailModel> {
        forgotPass = MutableLiveData<CheckPhoneEmailModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                apiInterface?.forgotPassword(data)
                    ?.enqueue(object : Callback<CheckPhoneEmailModel> {
                        override fun onFailure(call: Call<CheckPhoneEmailModel>, t: Throwable) {
                            Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(
                            call: Call<CheckPhoneEmailModel>,
                            response: Response<CheckPhoneEmailModel>,
                        ) {
                            if (response.body() != null) {
                                forgotPass.value = response.body()
                            }
                        }
                    })
            }
            else -> {
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return forgotPass
    }

    // Reset new Password
    private lateinit var resetPassword: MutableLiveData<CheckPhoneEmailModel>
    fun resetPassword(
        activity: Activity,
        data: HashMap<String, String>,
    ): LiveData<CheckPhoneEmailModel> {
        resetPassword = MutableLiveData<CheckPhoneEmailModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                apiInterface?.changePassword(data)
                    ?.enqueue(object : Callback<CheckPhoneEmailModel> {
                        override fun onFailure(call: Call<CheckPhoneEmailModel>, t: Throwable) {
                            Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(
                            call: Call<CheckPhoneEmailModel>,
                            response: Response<CheckPhoneEmailModel>,
                        ) {
                            if (response.body() != null) {
                                resetPassword.value = response.body()
                            }
                        }
                    })
            }
            else -> {
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return resetPassword
    }
    // change password

    private lateinit var changePassword: MutableLiveData<CheckPhoneEmailModel>

    fun changePassword(
        activity: Activity,
        data: HashMap<String, String>,
    ): LiveData<CheckPhoneEmailModel> {
        changePassword = MutableLiveData<CheckPhoneEmailModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                apiInterface?.resetPassword(data)?.enqueue(object : Callback<CheckPhoneEmailModel> {
                    override fun onFailure(call: Call<CheckPhoneEmailModel>, t: Throwable) {
                        Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(
                        call: Call<CheckPhoneEmailModel>,
                        response: Response<CheckPhoneEmailModel>,
                    ) {
                        if (response.body() != null) {
                            changePassword.value = response.body()
                        }
                    }
                })
            }
            else -> {
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return changePassword
    }
    // add address
    private lateinit var addUserAddress: MutableLiveData<CheckPhoneEmailModel>
    fun addUserAddress(
        activity: Activity,
        data: HashMap<String, String>,
    ): LiveData<CheckPhoneEmailModel> {
        addUserAddress = MutableLiveData<CheckPhoneEmailModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                apiInterface?.addUserAddress(data)
                    ?.enqueue(object : Callback<CheckPhoneEmailModel> {
                        override fun onFailure(call: Call<CheckPhoneEmailModel>, t: Throwable) {
                            Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(
                            call: Call<CheckPhoneEmailModel>,
                            response: Response<CheckPhoneEmailModel>,
                        ) {
                            if (response.body() != null) {
                                addUserAddress.value = response.body()
                            }
                        }
                    })
            }
            else -> {
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return addUserAddress
    }
    // Service new Password
    private lateinit var topService: MutableLiveData<ServiceListModel>
    fun serviceList(activity: Activity): LiveData<ServiceListModel> {
        topService = MutableLiveData<ServiceListModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                apiInterface?.serviceList()?.enqueue(object : Callback<ServiceListModel> {
                    override fun onFailure(call: Call<ServiceListModel>, t: Throwable) {
                        Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<ServiceListModel>,
                        response: Response<ServiceListModel>,
                    ) {
                        if (response.body() != null) {
                            topService.value = response.body()
                        }
                    }
                })
            }
            else -> {
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return topService
    }
    // getPostalCode
    private lateinit var getPostalCode: MutableLiveData<GetPostalCodeModel>
    fun getPostalCode(activity: Activity): LiveData<GetPostalCodeModel> {
        getPostalCode = MutableLiveData<GetPostalCodeModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                apiInterface1?.getPostalCode()?.enqueue(object : Callback<GetPostalCodeModel> {
                    override fun onFailure(call: Call<GetPostalCodeModel>, t: Throwable) {
                        Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(
                        call: Call<GetPostalCodeModel>,
                        response: Response<GetPostalCodeModel>,
                    ) {
                        if (response.body() != null) {
                            getPostalCode.value = response.body()
                        }
                    }
                })
            }
            else -> {
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return getPostalCode
    }

    // Service new Password
    private lateinit var citiesList: MutableLiveData<CitiesListModel>
    fun citiesList(activity: Activity): LiveData<CitiesListModel> {
        citiesList = MutableLiveData<CitiesListModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                apiInterface?.citiesList()?.enqueue(object : Callback<CitiesListModel> {
                    override fun onFailure(call: Call<CitiesListModel>, t: Throwable) {
                        Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<CitiesListModel>,
                        response: Response<CitiesListModel>,
                    ) {
                        if (response.body() != null) {
                            citiesList.value = response.body()
                        }
                    }
                })
            }
            else -> {
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return citiesList
    }

    // get user address

    private lateinit var getUserAddress: MutableLiveData<GetUserAddressModel>

    fun getUserAddress(
        activity: Activity,
        data: HashMap<String, String>,
    ): LiveData<GetUserAddressModel> {
        getUserAddress = MutableLiveData<GetUserAddressModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                apiInterface?.getUserAddress(data)?.enqueue(object : Callback<GetUserAddressModel> {
                    override fun onFailure(call: Call<GetUserAddressModel>, t: Throwable) {
                        Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<GetUserAddressModel>,
                        response: Response<GetUserAddressModel>,
                    ) {
                        if (response.body() != null) {
                            getUserAddress.value = response.body()
                        }
                    }
                })
            }
            else -> {
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return getUserAddress
    }

    // get delete address

    private lateinit var deleteUserAddress: MutableLiveData<GetUserAddressModel>

    fun deleteUserAddress(
        activity: Activity,
        data: HashMap<String, String>,
    ): LiveData<GetUserAddressModel> {
        deleteUserAddress = MutableLiveData<GetUserAddressModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                apiInterface?.deleteUserAddress(data)
                    ?.enqueue(object : Callback<GetUserAddressModel> {
                        override fun onFailure(call: Call<GetUserAddressModel>, t: Throwable) {
                            Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<GetUserAddressModel>,
                            response: Response<GetUserAddressModel>,
                        ) {
                            if (response.body() != null) {
                                deleteUserAddress.value = response.body()
                            }
                        }
                    })
            }
            else -> {
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return deleteUserAddress
    }

    // Upload user Info
    private lateinit var updateUserProfile: MutableLiveData<UpdateProfileModel>

    fun updateUserProfile(
        activity: Activity,
        data: HashMap<String, String>,
    ): LiveData<UpdateProfileModel> {
        updateUserProfile = MutableLiveData<UpdateProfileModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                CommonUtils.showProgress(activity)
                apiInterface?.updateProfile(data)
                    ?.enqueue(object : Callback<UpdateProfileModel> {
                        override fun onFailure(call: Call<UpdateProfileModel>, t: Throwable) {
                            CommonUtils.dismissProgress()
                            Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<UpdateProfileModel>,
                            response: Response<UpdateProfileModel>
                        ) {
                            CommonUtils.dismissProgress()
                            if (response.body() != null) {
                                updateUserProfile.value = response.body()
                            } else {
                                var error = response.errorBody().toString()
                                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
            }
            else -> {
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return updateUserProfile
    }

    // logout user
    private lateinit var logout: MutableLiveData<UpdateProfileModel>

    fun logout(
        activity: Activity,
        data: HashMap<String, String>,
    ): LiveData<UpdateProfileModel> {
        logout = MutableLiveData<UpdateProfileModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                CommonUtils.showProgress(activity)
                apiInterface?.logout(data)
                    ?.enqueue(object : Callback<UpdateProfileModel> {
                        override fun onFailure(call: Call<UpdateProfileModel>, t: Throwable) {
                            CommonUtils.dismissProgress()
                            Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<UpdateProfileModel>,
                            response: Response<UpdateProfileModel>
                        ) {
                            CommonUtils.dismissProgress()
                            if (response.body() != null) {
                                logout.value = response.body()
                            } else {
                                var error = response.errorBody().toString()
                                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
            }
            else -> {
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return logout
    }

    // updateImage user Info
    private lateinit var updateImage: MutableLiveData<LoginRegisterModel>

    fun updateImage(
        activity: Activity,
        userId: RequestBody,
        image: MultipartBody.Part
    ): LiveData<LoginRegisterModel> {
        updateImage = MutableLiveData<LoginRegisterModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                CommonUtils.showProgress(activity)
                apiInterface?.updateImage(userId, image)
                    ?.enqueue(object : Callback<LoginRegisterModel> {
                        override fun onFailure(call: Call<LoginRegisterModel>, t: Throwable) {
                            CommonUtils.dismissProgress()
                            Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<LoginRegisterModel>,
                            response: Response<LoginRegisterModel>
                        ) {
                            CommonUtils.dismissProgress()
                            if (response.body() != null) {
                                updateImage.value = response.body()
                            } else {
                                var error = response.errorBody().toString()
                                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
            }
            else -> {
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return updateImage
    }

    // userFeedback user
    private lateinit var userFeedback: MutableLiveData<UpdateProfileModel>

    fun userFeedback(
        activity: Activity,
        data: HashMap<String, String>,
    ): LiveData<UpdateProfileModel> {
        userFeedback = MutableLiveData<UpdateProfileModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                CommonUtils.showProgress(activity)
                apiInterface?.userFeedback(data)
                    ?.enqueue(object : Callback<UpdateProfileModel> {
                        override fun onFailure(call: Call<UpdateProfileModel>, t: Throwable) {
                            CommonUtils.dismissProgress()
                            Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<UpdateProfileModel>,
                            response: Response<UpdateProfileModel>
                        ) {
                            CommonUtils.dismissProgress()
                            if (response.body() != null) {
                                userFeedback.value = response.body()
                            } else {
                                var error = response.errorBody().toString()
                                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
            }
            else -> {
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return userFeedback
    }


    // userProfile user
    private lateinit var userProfile: MutableLiveData<UserProfileModel>

    fun userProfile(
        activity: Activity,
        data: HashMap<String, String>,
    ): LiveData<UserProfileModel> {
        userProfile = MutableLiveData<UserProfileModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
//                CommonUtils.showProgress(activity)
                apiInterface?.userProfile(data)
                    ?.enqueue(object : Callback<UserProfileModel> {
                        override fun onFailure(call: Call<UserProfileModel>, t: Throwable) {
//                            CommonUtils.dismissProgress()
                            Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<UserProfileModel>,
                            response: Response<UserProfileModel>
                        ) {
//                            CommonUtils.dismissProgress()
                            if (response.body() != null) {
                                userProfile.value = response.body()
                            } else {
                                var error = response.errorBody().toString()
                                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
            }
            else -> {
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return userProfile
    }


    // Booking order
    private lateinit var booking1: MutableLiveData<BookingOrderModel>
    fun bookingOrder(
        activity: Activity,
        data: HashMap<String, String>,
    ): LiveData<BookingOrderModel> {
        booking1 = MutableLiveData<BookingOrderModel>()
        when {
            CommonUtils.isNetworkConnected(activity) -> {
                apiInterface?.bookingOrder(data)
                    ?.enqueue(object : Callback<BookingOrderModel> {
                        override fun onFailure(call: Call<BookingOrderModel>, t: Throwable) {
                            Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(
                            call: Call<BookingOrderModel>,
                            response: Response<BookingOrderModel>
                        ) {
                            if (response.body() != null) {
                                booking1.value = response.body()
                            } else {
                                var error = response.errorBody().toString()
                                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
            }
            else -> {
                Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return booking1
    }
}