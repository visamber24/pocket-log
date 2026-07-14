package com.lazysloth.pocketlog.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.ZonedDateTime

@Entity(
    tableName = "transactionItem",
//    foreignKeys = [ForeignKey(
//        entity = User::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("userId"),
//        onUpdate = ForeignKey.CASCADE,
//        onDelete = ForeignKey.CASCADE
//    )],
    indices = [Index("userId")]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String? = "",
    val amount: Double = 0.0,
    val accountId: Long = 0,
    val categoryId: Long =  0,
    val transactionType: TransactionType = TransactionType.DEBIT,
    val note: String = "",
    val description: String = "",
    val dateTime: ZonedDateTime = ZonedDateTime.now(),
)


class Converters {
    @TypeConverter
    fun fromString(value: String?): ZonedDateTime? {
        return value?.let { ZonedDateTime.parse(it) }
    }

    @TypeConverter
    fun zonedDateTimeToString(zonedDateTime: ZonedDateTime?): String? {
        return zonedDateTime?.toString()
    }
}
