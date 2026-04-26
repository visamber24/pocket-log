package com.lazysloth.pocketlog.database.repository

import com.lazysloth.pocketlog.database.data.Category1
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun saveCategory(category1: Category1)
    suspend fun updateCategory(category1: Category1)
    suspend fun deleteCategory(category1: Category1)
//    fun getCategoryByUserId(userId:Int): Flow<List<Category1>>
    fun getCategoryByCategoryId(id: Long): Flow<Category1>
}