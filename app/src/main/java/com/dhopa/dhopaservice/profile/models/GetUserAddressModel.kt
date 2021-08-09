package com.dhopa.dhopaservice.profile.models

data class GetUserAddressModel(
    val details: List<AddressDetail>,
    val message: String,
    val success: String
)

data class AddressDetail(
    val address1: String,
    val address2: String,
    val city: String,
    val country: String,
    val created: String,
    val id: String,
    val latitude: String,
    val longitude: String,
    val postalCode: String,
    val type: String,
    val userId: String
)