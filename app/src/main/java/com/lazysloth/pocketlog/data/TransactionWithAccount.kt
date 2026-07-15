package com.lazysloth.pocketlog.data

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionWithAccount(
    @Embedded
val transaction: Transaction,


    @Relation(
        parentColumn = "accountId",
        entityColumn = "id"
    )
    val account: Account?,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: Category1?,
)
