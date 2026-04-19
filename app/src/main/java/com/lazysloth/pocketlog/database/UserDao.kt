package com.lazysloth.pocketlog.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lazysloth.pocketlog.database.data.User

@Dao
interface UserDao {
    @Query("SELECT username FROM users WHERE id=:id")
    suspend fun getUsernameById(id: Int): String?
    @Insert
    suspend fun saveUser(user: User)
    @Query("SELECT EXISTS(SELECT * FROM users )")
    suspend fun checkUsers() : Boolean
    @Query("SELECT id FROM users WHERE username = :username")
    suspend fun getIdByUsername(username: String): Int?
    @Query("SELECT id FROM users WHERE emailId = :emailId")
    suspend fun getIdByEmail(emailId: String): Int?
    @Query("SELECT * FROM users WHERE emailId = :emailId AND password = :password")
    suspend fun login(emailId : String,password: String): User?
    @Query("SELECT password FROM  users WHERE emailId = :emailId")
    suspend fun getPasswordByEmailId(emailId: String): String?
    @Query("SELECT password FROM  users WHERE username = :emailId")
    suspend fun getPasswordByUsername(emailId: String): String?
}