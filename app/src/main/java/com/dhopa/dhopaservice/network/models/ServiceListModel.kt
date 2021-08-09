package com.dhopa.dhopaservice.network.models

data class ServiceListModel(
   val details: List<Detail>,
    val message: String,
    val success: String
)

data class Detail(
    val created: String,
    val description: String,
    val id: String,
    val image: String,
    val status: String,
    val title: String,
    val updated: String
)