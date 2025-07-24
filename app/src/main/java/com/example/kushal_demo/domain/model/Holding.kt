package com.example.kushal_demo.domain.model

data class Holding(
    val name: String,
    val quantity: Double,
    val avgPrice: Double,
    val currentPrice: Double,
    val closePrice: Double
) {
    val investment: Double
        get() = quantity * avgPrice

    val currentValue: Double
        get() = quantity * currentPrice

    val pnl: Double
        get() = currentValue - investment

    val todayPnl: Double
        get() = (currentPrice - closePrice) * quantity
}

