package com.narayan.singh.scottishpowerandroidtest.common.arch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<T : ViewModelUIState>(initialUIState: T) : ViewModel() {

    protected val _uiState = MutableStateFlow(initialUIState)
    val uiState: StateFlow<T> = _uiState.asStateFlow()
}