package com.example.kushal_demo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kushal_demo.domain.model.Holding
import com.example.kushal_demo.domain.usercase.GetHoldingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val useCase: GetHoldingsUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading


    private val _state = MutableStateFlow<List<Holding>>(emptyList())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {

            _state.value = useCase()
        }
    }
}
