package com.lazysloth.pocketlog.ui.screen.home.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.database.data.Category1
import com.lazysloth.pocketlog.database.data.CategoryType
import com.lazysloth.pocketlog.ui.screen.home.uiState.AddCategoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoryScreenViewModel: ViewModel() {

    val uiState = MutableStateFlow(CategoryListUiState()).asStateFlow()
    val addCategoryUiState = AddCategoryUiState()
    fun getIconRes(key: String): Int{
        return addCategoryUiState.categoryIcon.find { it.key == key }?.iconRes
            ?:R.drawable.delete_24px
    }
}
data class CategoryListUiState(
    val categoryList: List<Category1> = listOf(
        Category1(icon = "ic_food", name = "food", type = CategoryType.EXPENSE,),
        Category1(icon = "ic_shopping", name = "shopping", type = CategoryType.EXPENSE,),
        Category1(icon = "ic_gift", name = "gift", type = CategoryType.EXPENSE,),
        Category1(icon = "ic_health", name = "health", type = CategoryType.EXPENSE,),
        Category1(icon = "ic_entertainment", name = "entertainment", type = CategoryType.EXPENSE,),
        Category1(icon = "ic_bills", name = "bills", type = CategoryType.EXPENSE,)
    )

)