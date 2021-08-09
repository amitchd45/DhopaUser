package com.dhopa.dhopaservice.network

import com.dhopa.dhopaservice.network.models.*
import com.dhopa.dhopaservice.profile.models.GetUserAddressModel
import com.dhopa.dhopaservice.profile.models.UpdateProfileModel
import com.dhopa.dhopaservice.profile.models.UserProfileModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


const val BASE_URL1 = "https://api.getAddress.io/find/"
const val API_KEY = "dF6GeHSSxUi8e1q3YvudLA31895"

interface ApiInterface {

    @GET("sw1a2aa?expand=true&api-key=$API_KEY")
    fun getPostalCode(): Call<GetPostalCodeModel>

    @FormUrlEncoded
    @POST("userUniqueApi")
    fun checkPhoneEmail(@FieldMap data: HashMap<String, String>): Call<CheckPhoneEmailModel>


    @FormUrlEncoded
    @POST("userRegister")
    fun register(@FieldMap data: HashMap<String, String>): Call<LoginRegisterModel>

    @FormUrlEncoded
    @POST("login")
    fun login(@FieldMap data: HashMap<String, String>): Call<LoginRegisterModel>

    @FormUrlEncoded
    @POST("userForgotPassword")
    fun forgotPassword(@FieldMap data: HashMap<String, String>): Call<CheckPhoneEmailModel>


    @FormUrlEncoded
    @POST("userUpdatePassword")
    fun resetPassword(@FieldMap data: HashMap<String, String>): Call<CheckPhoneEmailModel>

    @GET("serviceList")
    fun serviceList(): Call<ServiceListModel>

    @GET("citiesList")
    fun citiesList(): Call<CitiesListModel>

    // Todo change password Api

    @FormUrlEncoded
    @POST("changePassword")
    fun changePassword(@FieldMap data: HashMap<String, String>): Call<CheckPhoneEmailModel>

    @FormUrlEncoded
    @POST("addUserAddress")
    fun addUserAddress(@FieldMap data: HashMap<String, String>): Call<CheckPhoneEmailModel>

    @FormUrlEncoded
    @POST("getUserAddress")
    fun getUserAddress(@FieldMap data: HashMap<String, String>): Call<GetUserAddressModel>

    @FormUrlEncoded
    @POST("deleteUserAddress")
    fun deleteUserAddress(@FieldMap data: HashMap<String, String>): Call<GetUserAddressModel>

    @FormUrlEncoded
    @POST("updateProfile")
    fun updateProfile(@FieldMap data: HashMap<String, String>): Call<UpdateProfileModel>

    @Multipart
    @POST("updateImage")
    fun updateImage(
        @Part("userId") userId: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<LoginRegisterModel>

    @FormUrlEncoded
    @POST("logout")
    fun logout(@FieldMap data: HashMap<String, String>): Call<UpdateProfileModel>

    @FormUrlEncoded
    @POST("userFeedback")
    fun userFeedback(@FieldMap data: HashMap<String, String>): Call<UpdateProfileModel>

    @FormUrlEncoded
    @POST("userProfile")
    fun userProfile(@FieldMap data: HashMap<String, String>): Call<UserProfileModel>

    @FormUrlEncoded
    @POST("socialLogin")
    fun socialLogin(@FieldMap data: HashMap<String, String>): Call<LoginRegisterModel>

    @FormUrlEncoded
    @POST("booking")
    fun bookingOrder(@FieldMap data: HashMap<String, String>): Call<BookingOrderModel>

}