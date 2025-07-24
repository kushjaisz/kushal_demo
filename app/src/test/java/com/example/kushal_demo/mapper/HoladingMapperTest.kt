package com.example.kushal_demo.mapper

import com.example.kushal_demo.data.dto.HoldingDto
import com.example.kushal_demo.data.mapper.toDomain
import com.example.kushal_demo.data.mapper.toEntity
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class HoldingMapperTest {

    @Test
    fun `map DTO to entity to domain correctly`() {
        val dto = HoldingDto("INFY", 20.00, 1300.0, 1250.0, 1200.0)
        val entity = dto.toEntity()
        val domain = entity.toDomain()

        assertEquals("INFY", domain.name)
        assertEquals(25000.0, domain.currentValue, 0.01)
        assertEquals(26000.0, domain.investment, 0.01)
        assertEquals(-1000.0, domain.pnl, 0.01)
        assertEquals(1000.0, domain.todayPnl, 0.01)
    }

    @Test
    fun `map zero quantity holding`() {
        val dto = HoldingDto("ZERO", 0.0, 1000.0, 1100.0, 1050.0)
        val domain = dto.toEntity().toDomain()

        assertEquals("ZERO", domain.name)
        assertEquals(0.0, domain.quantity)
        assertEquals(0.0, domain.currentValue, 0.01)
        assertEquals(0.0, domain.investment, 0.01)
        assertEquals(0.0, domain.pnl, 0.01)
        assertEquals(0.0, domain.todayPnl, 0.01)
    }

    @Test
    fun `map negative pnl case`() {
        val dto = HoldingDto("LOSS", 5.0, 1000.0, 800.0, 850.0)
        val domain = dto.toEntity().toDomain()

        assertEquals("LOSS", domain.name)
        assertEquals(5000.0, domain.investment, 0.01)
        assertEquals(4000.0, domain.currentValue, 0.01)
        assertEquals(-1000.0, domain.pnl, 0.01)
        assertEquals(-250.0, domain.todayPnl, 0.01)
    }

    @Test
    fun `map profit pnl case`() {
        val dto = HoldingDto("GAIN", 2.0, 500.0, 800.0, 750.0)
        val domain = dto.toEntity().toDomain()

        assertEquals(1600.0, domain.currentValue, 0.01)
        assertEquals(1000.0, domain.investment, 0.01)
        assertEquals(600.0, domain.pnl, 0.01)
        assertEquals(100.0, domain.todayPnl, 0.01)
    }

    @Test
    fun `map same current and close price means no today PnL`() {
        val dto = HoldingDto("STATIC", 3.0, 900.0, 950.0, 950.0)
        val domain = dto.toEntity().toDomain()

        assertEquals(2850.0, domain.currentValue, 0.01)
        assertEquals(0.0, domain.todayPnl, 0.01)
    }
}
