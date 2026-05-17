package com.lazysloth.pocketlog.database.repository

import com.lazysloth.pocketlog.data.User
import com.lazysloth.pocketlog.database.UserDao

class UserRepository(val userDao: UserDao) {
    suspend fun checkUser() = userDao.checkUsers()
    suspend fun getUsernameById(id: String?) = userDao.getUsernameById(id)
    suspend fun getIdByUsername(username: String)  = userDao.getIdByUsername(username)
    suspend fun getIdByEmailId(emailId: String) = userDao.getIdByEmail(emailId)
    suspend fun saveUser(user: User) = userDao.saveUser(user)
    suspend fun getUserPasswordByEmail(emailId: String) = userDao.getPasswordByEmailId(emailId)
    suspend fun getUserPasswordByUsername(username: String) = userDao.getPasswordByUsername(username)
}