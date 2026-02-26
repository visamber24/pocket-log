package com.lazysloth.pocketlog.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val username : String = "",
    val emailId: String = "",
    val password: String= "",

) {
}