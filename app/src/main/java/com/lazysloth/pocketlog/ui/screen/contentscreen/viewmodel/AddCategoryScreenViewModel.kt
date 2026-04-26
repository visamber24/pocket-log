package com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.database.data.Category1
import com.lazysloth.pocketlog.database.data.CategoryType
import com.lazysloth.pocketlog.database.repository.CategoryRepository
import kotlinx.coroutines.launch


class AddCategoryScreenViewModel(private val repository: CategoryRepository) : ViewModel() {

    // UI State for the Composable
    var name by mutableStateOf("")
    var selectedType by mutableStateOf(CategoryType.EXPENSE) // Default

    fun onNameChange(newName: String) { name = newName }
    fun onTypeChange(type: CategoryType) { selectedType = type }

    fun saveCategory(userId: Int) {
        if (name.isBlank()) return

        viewModelScope.launch {
            val newCategory = Category1(
                name = name,
                type = selectedType
            )
            repository.saveCategory(newCategory)
            // Reset fields after save
            name = ""
        }
    }
}
