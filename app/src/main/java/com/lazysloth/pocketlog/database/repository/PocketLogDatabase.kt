package com.lazysloth.pocketlog.database.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lazysloth.pocketlog.database.AccountDao
import com.lazysloth.pocketlog.database.TransactionItemDao
import com.lazysloth.pocketlog.database.UserDao
import com.lazysloth.pocketlog.database.data.Account1
import com.lazysloth.pocketlog.database.data.Converters
import com.lazysloth.pocketlog.database.data.Transaction
import com.lazysloth.pocketlog.database.data.User


@Database(entities = [Transaction::class, User::class, Account1::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PocketLogDatabase : RoomDatabase() {
    abstract fun getTransactionItem(): TransactionItemDao
    abstract fun userDao(): UserDao
    abstract fun getAccount(): AccountDao

    companion object {
        @Volatile
        private var Instance: PocketLogDatabase? = null
        fun getDatabase(context: Context): PocketLogDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    PocketLogDatabase::class.java,
                    name = "pocketLog_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }

            }
        }
    }

}