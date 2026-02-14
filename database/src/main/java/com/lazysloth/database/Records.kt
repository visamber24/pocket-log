package com.lazysloth.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lazysloth.pocketlog.data.Category
import com.lazysloth.pocketlog.data.TransactionType
import java.util.Locale

@Entity(tableName = "items")
data class Records(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val account: String,
    val category: Category,
    val transactionType: TransactionType,
    val note : String,
    val description : String,
    val date_time : Int
)