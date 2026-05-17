package com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.data.Category1
import com.lazysloth.pocketlog.data.CategoryType
import com.lazysloth.pocketlog.database.repository.CategoryRepository
import com.lazysloth.pocketlog.di.UserPersists
import com.lazysloth.pocketlog.ui.screen.home.uiState.AddCategoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class AddCategoryScreenViewModel(
    private val repository: CategoryRepository,
    private val userPersists: UserPersists,
) : ViewModel() {


    private val _uiState = MutableStateFlow(AddCategoryUiState())
    val uiState: StateFlow<AddCategoryUiState> = _uiState.asStateFlow()
    val selectedIcon = MutableStateFlow<String?>(null)

    // UI State for the Composable
    var name by mutableStateOf("")
    var selectedType by mutableStateOf(CategoryType.EXPENSE) // Default

    fun onNameChange(newName: String) {
        name = newName
    }

    fun onTypeChange(type: CategoryType) {
        selectedType = type
    }

    fun saveCategory() {
        val userId = userPersists.currentId
        if (name.isBlank()) return

        viewModelScope.launch {
            val newCategory = Category1(
                userId = userId,
                name = name,
                icon = selectedIcon.value!!,
                type = selectedType
            )
            repository.saveCategory(newCategory)
            // Reset fields after save
            name = ""
        }
    }

}
