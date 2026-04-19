package com.lazysloth.pocketlog.database

import androidx.room.Embedded
import androidx.room.Relation
import com.lazysloth.pocketlog.database.data.Transaction
import com.lazysloth.pocketlog.database.data.User

data class TransactionByUser(
    @Embedded val id : User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val transactionById: List<Transaction>? = null
)
