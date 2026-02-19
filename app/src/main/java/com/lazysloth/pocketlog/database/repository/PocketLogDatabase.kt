package com.lazysloth.pocketlog.database.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lazysloth.pocketlog.database.Transaction
import com.lazysloth.pocketlog.database.TransactionItemDao


@Database(entities = [Transaction :: class], version = 1, exportSchema = false)
abstract class PocketLogDatabase : RoomDatabase() {
    abstract fun getTransactionItem() : TransactionItemDao

    companion object {
        @Volatile
        private var Instance : PocketLogDatabase? = null
        fun getDatabase(context : Context) : PocketLogDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context = context, PocketLogDatabase::class.java, name = "pocketLog_database")
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also { Instance = it}

            }
        }
    }

}