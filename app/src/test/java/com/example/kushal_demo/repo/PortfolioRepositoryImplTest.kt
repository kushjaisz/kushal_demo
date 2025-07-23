package com.example.kushal_demo.repo

import com.example.kushal_demo.data.api.PortfolioAPIService
import com.example.kushal_demo.data.dto.HoldingDto
import com.example.kushal_demo.data.dto.HoldingsResponse
import com.example.kushal_demo.data.dto.UserHoldingData
import com.example.kushal_demo.data.localDB.HoldingDao
import com.example.kushal_demo.data.localDB.HoldingEntity
import com.example.kushal_demo.data.repo.PortfolioRepositoryImpl
import com.example.kushal_demo.domain.model.Holding
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import kotlin.test.Test



@ExperimentalCoroutinesApi
class PortfolioRepositoryImplTest {

    private val api = mock<PortfolioAPIService>()
    private val dao = mock<HoldingDao>()

    private lateinit var repository: PortfolioRepositoryImpl

    @Before
    fun setup() {
        repository = PortfolioRepositoryImpl(api, dao)
    }

    @Test
    fun `when API succeeds, data is saved to DB and returned`() = runTest {
        val dtoList = listOf(HoldingDto("ICICI", 100.0, 110.0, 100.0, 105.0))
        val response = HoldingsResponse(data = UserHoldingData(userHolding = dtoList))

        whenever(api.getHoldings()).thenReturn(response)
        whenever(dao.insertHoldings(any())).thenReturn(Unit)

        val result = repository.getHoldings()

        assertEquals("ICICI", result.first().name)
    }

    @Test
    fun `when API fails, fallback to local DB`() = runTest {
        whenever(api.getHoldings()).thenThrow(RuntimeException("Network error"))
        val cached = listOf(HoldingEntity("ICICI", 100.0, 110.0, 100.0, 105.0))
        whenever(dao.getHoldings()).thenReturn(cached)

        val result = repository.getHoldings()

        assertEquals("ICICI", result.first().name)
    }

    @Test
    fun `when API returns empty list, result is also empty`() = runTest {
        val emptyList = emptyList<HoldingDto>()
        val response = HoldingsResponse(data = UserHoldingData(userHolding = emptyList))

        whenever(api.getHoldings()).thenReturn(response)
        whenever(dao.insertHoldings(any())).thenReturn(Unit)

        val result = repository.getHoldings()

        assertEquals(0, result.size)
    }
}
