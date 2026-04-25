package com.lazysloth.pocketlog.database.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

enum class
Account(
) {
    Cash,
    DEBIT_CARD,
    CREDIT_CARD,
    UPI,
    UPI_LITE,
}

@Entity(
    tableName = "accounts",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("userId"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )
    ],
    indices = [Index("userId")]
)
data class Account1(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Int = 0,
    val name: String = "",
    val type: Account,
    val balance: Double = 0.0,
    val currentBalance: Double = balance,
)