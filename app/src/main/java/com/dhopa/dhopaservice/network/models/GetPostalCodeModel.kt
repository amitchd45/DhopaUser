package com.dhopa.dhopaservice.network.models

data class GetPostalCodeModel(
    val addresses: List<Addresse>,
    val latitude: Double,
    val longitude: Double,
    val postcode: String
)

data class Addresse(
    val building_name: String,
    val building_number: String,
    val country: String,
    val county: String,
    val district: String,
    val formatted_address: List<String>,
    val line_1: String,
    val line_2: String,
    val line_3: String,
    val line_4: String,
    val locality: String,
    val sub_building_name: String,
    val sub_building_number: String,
    val thoroughfare: String,
    val town_or_city: String
)