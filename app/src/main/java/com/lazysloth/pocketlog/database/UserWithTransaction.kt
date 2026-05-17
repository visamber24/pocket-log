package com.lazysloth.pocketlog.database

import androidx.room.Embedded
import androidx.room.Relation
import com.lazysloth.pocketlog.data.Transaction
import com.lazysloth.pocketlog.data.User

data class TransactionByUser(
    @Embedded val id : User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val transactionById: List<Transaction>? = null
)
