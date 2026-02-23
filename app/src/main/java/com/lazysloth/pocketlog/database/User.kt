package com.lazysloth.pocketlog.database

import androidx.room.Entity

@Entity(tableName = "users")
data class User(
    val id : Int = 0,
    val username : String = "",
    val emailId: String = "",
    val password: String= "",

) {
}