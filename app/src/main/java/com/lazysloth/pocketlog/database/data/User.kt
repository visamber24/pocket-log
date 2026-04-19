package com.lazysloth.pocketlog.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val username : String = "",
    val firstName:String = "",
    val lastName: String= "",
    val emailId: String = "",
    val password: String= "",

) {
}