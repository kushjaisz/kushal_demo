package com.example.kushal_demo.data.dto

import com.example.kushal_demo.domain.model.Holding
import com.google.gson.annotations.SerializedName

data class HoldingsResponse(
    val data: UserHoldingData
)

data class UserHoldingData(
    val userHolding: List<HoldingDto>
)
data class HoldingDto(
    @SerializedName("symbol") val name: String,
    @SerializedName("quantity") val quantity: Double,
    @SerializedName("avgPrice") val avgPrice: Double,
    @SerializedName("ltp") val currentPrice: Double,
    @SerializedName("close") val close: Double
)

