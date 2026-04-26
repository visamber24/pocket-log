package com.lazysloth.pocketlog.database.repository

import com.lazysloth.pocketlog.database.CategoryDao
import com.lazysloth.pocketlog.database.data.Category1
import kotlinx.coroutines.flow.Flow

class OfflineCategoryRepository(private val categoryDao: CategoryDao) : CategoryRepository{
    override suspend fun saveCategory(category1: Category1) {
        categoryDao.insert(category1)
    }

    override suspend fun updateCategory(category1: Category1) {
       categoryDao.update(category1)
    }

    override suspend fun deleteCategory(category1: Category1) {
        categoryDao.delete(category1)
    }

//    override fun getCategoryByUserId(userId: Int): Flow<List<Category1>> {
//        return categoryDao.getCategoryByUserId(userId)
//    }

    override fun getCategoryByCategoryId(id: Long): Flow<Category1> {
        return categoryDao.getCategoryByCategoryId(id)
    }
}