package com.lazysloth.pocketlog.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lazysloth.pocketlog.database.data.Category
import com.lazysloth.pocketlog.database.data.TransactionType
import com.lazysloth.pocketlog.ui.screen.home.uiState.Account
import java.util.Date
import androidx.room.TypeConverter
import java.time.ZonedDateTime

@Entity(tableName = "items")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
//    val userId: String,
    val amount: Double,
    val account: Account,
    val category: Category,
    val transactionType: TransactionType,
    val note : String,
    val description : String,
    val dateTime : ZonedDateTime,
)



class Converters {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromString(value: String?): ZonedDateTime? {
        return value?.let { ZonedDateTime.parse(it) }
    }

    @TypeConverter
    fun zonedDateTimeToString(zonedDateTime: ZonedDateTime?): String? {
        return zonedDateTime?.toString()
    }
}
