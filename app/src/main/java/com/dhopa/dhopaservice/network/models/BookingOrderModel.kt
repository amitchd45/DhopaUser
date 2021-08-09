package com.dhopa.dhopaservice.network.models

data class BookingOrderModel(
    val details: BookingDetails,
    val message: String,
    val success: String
)

data class BookingDetails(
    val orderId: String,
    val subTotalAmount: String,
    val tax: String,
    val totalAmount: String
)