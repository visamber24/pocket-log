package com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel

import androidx.lifecycle.ViewModel
import com.lazysloth.pocketlog.ui.screen.home.uiState.AddAccountUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddAccountViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AddAccountUiState())
    val uiState: StateFlow<AddAccountUiState> = _uiState.asStateFlow()


}