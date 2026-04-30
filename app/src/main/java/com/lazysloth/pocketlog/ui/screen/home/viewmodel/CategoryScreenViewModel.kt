package com.lazysloth.pocketlog.ui.screen.home.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.database.data.Category1
import com.lazysloth.pocketlog.database.data.CategoryType
import com.lazysloth.pocketlog.database.repository.CategoryRepository
import com.lazysloth.pocketlog.di.UserPersists
import com.lazysloth.pocketlog.ui.screen.home.uiState.AddCategoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CategoryScreenViewModel(
    private val categoryRepository: CategoryRepository,
    private val userPersists: UserPersists
) : ViewModel() {

    val uiState: StateFlow<CategoryListUiState> =
        categoryRepository.getCategoryByUserId(userPersists.currentId)
            .map {
                CategoryListUiState(it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CategoryListUiState()
            )
    val addCategoryUiState = AddCategoryUiState()
    fun getIconRes(key: String): Int {
        return addCategoryUiState.categoryIcon.find { it.key == key }?.iconRes
            ?: R.drawable.delete_24px
    }
}

data class CategoryListUiState(
    val categoryList: List<Category1> = listOf(
//        Category1(icon = "ic_food", name = "food", type = CategoryType.EXPENSE,),
//        Category1(icon = "ic_shopping", name = "shopping", type = CategoryType.EXPENSE,),
//        Category1(icon = "ic_gift", name = "gift", type = CategoryType.EXPENSE,),
//        Category1(icon = "ic_health", name = "health", type = CategoryType.EXPENSE,),
//        Category1(icon = "ic_entertainment", name = "entertainment", type = CategoryType.EXPENSE,),
//        Category1(icon = "ic_bills", name = "bills", type = CategoryType.EXPENSE,)
    )

)