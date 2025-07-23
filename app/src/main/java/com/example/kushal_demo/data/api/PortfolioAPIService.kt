package com.example.kushal_demo.data.api

import com.example.kushal_demo.data.dto.HoldingsResponse
import retrofit2.http.GET

interface PortfolioAPIService {
    @GET("/")
    suspend fun getHoldings(): HoldingsResponse

}