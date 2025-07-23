package com.example.kushal_demo.data.mapper

import com.example.kushal_demo.data.dto.HoldingDto
import com.example.kushal_demo.data.localDB.HoldingEntity
import com.example.kushal_demo.domain.model.Holding


fun HoldingDto.toEntity() = HoldingEntity(name, quantity, avgPrice, currentPrice,close)
fun HoldingEntity.toDomain() = Holding(name, quantity, avgPrice, currentPrice,close)
