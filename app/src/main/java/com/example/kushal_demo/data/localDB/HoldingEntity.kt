package com.example.kushal_demo.data.localDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HoldingEntity(
    @PrimaryKey val name: String,
    val quantity: Double,
    val avgPrice: Double,
    val currentPrice: Double,
    val close:Double
)

