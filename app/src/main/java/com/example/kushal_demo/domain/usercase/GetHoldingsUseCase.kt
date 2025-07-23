package com.example.kushal_demo.domain.usercase

import com.example.kushal_demo.domain.model.Holding
import com.example.kushal_demo.domain.repo.PortfolioRepository
import javax.inject.Inject

class GetHoldingsUseCase @Inject constructor(
    private val repository: PortfolioRepository
) {
    suspend operator fun invoke(): List<Holding> = repository.getHoldings()
}
