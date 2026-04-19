package com.lazysloth.pocketlog.database.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.ZonedDateTime

@Entity(
    tableName = "items",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("userId"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("userId")]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int?,
    val amount: Double,
    val account: Account,
    val category: Category,
    val transactionType: TransactionType,
    val note: String,
    val description: String,
    val dateTime: ZonedDateTime,
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
