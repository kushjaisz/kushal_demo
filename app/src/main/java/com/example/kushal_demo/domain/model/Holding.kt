package com.example.kushal_demo.domain.model

data class Holding(
    val name: String,
    val quantity: Double,
    val avgPrice: Double,
    val currentPrice: Double,
    val closePrice: Double
) {
    val investment: Double
        get() = "%.3f".format(quantity * avgPrice).toDouble()

    val currentValue: Double
        get() = "%.3f".format(quantity * currentPrice).toDouble()

    val pnl: Double
        get() = "%.5f".format(currentValue - investment).toDouble()

    val todayPnl: Double
        get() = "%.3f".format((currentPrice - closePrice) * quantity).toDouble()
}

