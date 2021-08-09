package com.dhopa.dhopaservice.profile.models

data class UpdateProfileModel(
    val details: Details,
    val message: String,
    val success: String
)

data class Details(
    val created: String,
    val device_type: String,
    val email: String,
    val id: String,
    val image: String,
    val lastName: String,
    val latitude: String,
    val login_type: String,
    val longitude: String,
    val name: String,
    val password: String,
    val phone: String,
    val reg_id: String,
    val social_id: String,
    val updated: String
)