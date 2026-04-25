package com.lazysloth.pocketlog.database.data

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.Update

data class TransactionWithAccount(
    @Embedded
val transaction: Transaction,


    @Relation(
        parentColumn = "accountId",
        entityColumn = "id"
    )
    val account: Account1
)
