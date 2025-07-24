package com.example.kushal_demo.presentation.viewmodel

import com.example.kushal_demo.domain.model.Holding
import com.example.kushal_demo.domain.usercase.GetHoldingsUseCase
import com.example.kushal_demo.viewmodel.DispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class PortfolioViewModelTest {

    @get:Rule
    val dispatcherRule = DispatcherRule()

    private lateinit var getHoldingsUseCase: GetHoldingsUseCase
    private lateinit var viewModel: PortfolioViewModel

    @Before
    fun setUp() {
        getHoldingsUseCase = mock()
    }

    @Test
    fun `state should emit holdings from useCase`() = runTest {

        val mockHoldings = listOf(
            Holding("TCS", 10.0, 3000.0, 3200.0, 3100.0),
            Holding("INFY", 5.0, 1400.0, 1500.0, 1450.0)
        )
        `when`(getHoldingsUseCase()).thenReturn(mockHoldings)


        viewModel = PortfolioViewModel(getHoldingsUseCase)


        val result = viewModel.state.first()
        assertEquals(mockHoldings, result)
    }

    @Test
    fun `state should be empty when useCase returns empty list`() = runTest {

        `when`(getHoldingsUseCase()).thenReturn(emptyList())


        viewModel = PortfolioViewModel(getHoldingsUseCase)


        val result = viewModel.state.first()
        assertEquals(emptyList<Holding>(), result)
    }

    @Test
    fun `state should emit items in correct order`() = runTest {
        val mockHoldings = listOf(
            Holding("ZETA", 10.0, 100.0, 110.0, 105.0),
            Holding("ALPHA", 5.0, 200.0, 210.0, 205.0)
        )
        `when`(getHoldingsUseCase()).thenReturn(mockHoldings)

        viewModel = PortfolioViewModel(getHoldingsUseCase)

        val result = viewModel.state.first()
        assertEquals("ZETA", result[0].name)
        assertEquals("ALPHA", result[1].name)
    }

}
