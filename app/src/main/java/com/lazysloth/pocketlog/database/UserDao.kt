package com.lazysloth.pocketlog.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun saveUser(user: User)
    @Query("SELECT * FROM users WHERE emailId = :emailId AND password = :password")
    suspend fun login(emailId : String,password: String): User?
    @Query("SELECT password FROM  users WHERE emailId = :emailId")
    suspend fun getPasswordByEmailId(emailId: String): String?
    @Query("SELECT password FROM  users WHERE username = :emailId")
    suspend fun getPasswordByUsername(emailId: String): String?
}