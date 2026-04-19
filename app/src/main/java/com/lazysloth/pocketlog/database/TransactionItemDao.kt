package com.lazysloth.pocketlog.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lazysloth.pocketlog.database.data.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)
    @Query("DELETE FROM items WHERE id=:id")
    suspend fun deleteById(id:Int?)
    @Update
    suspend fun update(transaction: Transaction)

    @Query("SELECT * from items WHERE id = :id ")
    fun getTransaction(id: Int) : Flow<Transaction?>
    @androidx.room.Transaction
    @Query("SELECT * FROM items WHERE userId = :userId ORDER BY id ASC")
    fun getAllTransactionByUserId(userId: Int?) : Flow<List<Transaction>>
}