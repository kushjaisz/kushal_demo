package com.example.kushal_demo.domain.repo

import com.example.kushal_demo.domain.model.Holding
import com.example.kushal_demo.domain.utils.Resource

interface PortfolioRepository {
    suspend fun getHoldings(): List<Holding>
}
