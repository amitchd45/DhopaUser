package com.dhopa.dhopaservice.network.models

data class CitiesListModel(
    val details: List<CityList>,
    val message: String,
    val success: String
)

data class CityList(
    val id: String,
    val name: String,
    val state_id: String
)