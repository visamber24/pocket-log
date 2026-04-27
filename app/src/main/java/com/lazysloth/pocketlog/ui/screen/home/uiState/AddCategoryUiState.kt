package com.lazysloth.pocketlog.ui.screen.home.uiState

import com.lazysloth.pocketlog.R

data class AddCategoryUiState(
    val categoryIcon: List<CategoryIcon> = listOf(
        CategoryIcon(key = "ic_food", iconRes = R.drawable.ic_food),
        CategoryIcon("ic_shopping", R.drawable.ic_shopping),
        CategoryIcon("ic_transport", R.drawable.ic_transport),
//        CategoryIcon("ic_salary", R.drawable.ic_salary),
        CategoryIcon("ic_gift", R.drawable.ic_gift),
        CategoryIcon("ic_health", R.drawable.ic_health),
        CategoryIcon("ic_entertainment", R.drawable.ic_entertainment),
        CategoryIcon("ic_bills", R.drawable.ic_bill),
        CategoryIcon("ic_education", R.drawable.ic_education),
        CategoryIcon("ic_travel", R.drawable.ic_travel)
    ),
)
