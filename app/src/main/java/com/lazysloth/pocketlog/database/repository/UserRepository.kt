package com.lazysloth.pocketlog.database.repository

import com.lazysloth.pocketlog.database.User
import com.lazysloth.pocketlog.database.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepository(val userDao: UserDao) {
    suspend fun saveUser(user: User) = userDao.saveUser(user)
    suspend fun getUserPassword(emailId: String) = userDao.getPasswordByEmailId(emailId)
}