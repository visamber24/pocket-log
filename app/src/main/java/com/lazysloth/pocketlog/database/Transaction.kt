package com.lazysloth.pocketlog.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lazysloth.pocketlog.database.data.Category
import com.lazysloth.pocketlog.database.data.TransactionType

@Entity(tableName = "items")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val account: String?,
    val category: Category,
    val transactionType: TransactionType,
    val note : String,
    val description : String,
//    val date_time : Int
)