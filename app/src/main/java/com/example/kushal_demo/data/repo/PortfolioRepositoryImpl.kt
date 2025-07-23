package com.example.kushal_demo.data.repo

import android.util.Log
import com.example.kushal_demo.data.api.PortfolioAPIService
import com.example.kushal_demo.data.localDB.HoldingDao
import com.example.kushal_demo.data.mapper.toDomain
import com.example.kushal_demo.data.mapper.toEntity
import com.example.kushal_demo.domain.model.Holding
import com.example.kushal_demo.domain.repo.PortfolioRepository
import com.example.kushal_demo.domain.utils.Resource
import javax.inject.Inject

class PortfolioRepositoryImpl @Inject constructor(
    private val api: PortfolioAPIService,
    private val dao: HoldingDao
) : PortfolioRepository {

override suspend fun getHoldings(): List<Holding> {
    return try {
        val response = api.getHoldings()
        val entities = response.data.userHolding.map { it.toEntity() }
        dao.insertHoldings(entities)
        entities.map { it.toDomain() }
    } catch (e: Exception) {
        dao.getHoldings().map { it.toDomain() }
    }
}

}