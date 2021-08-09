package com.dhopa.dhopaservice.network.models

data class CheckPhoneEmailModel(
    val message: String,
    val otp: String,
    val success: String
)